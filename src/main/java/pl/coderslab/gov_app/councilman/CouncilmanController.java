package pl.coderslab.gov_app.councilman;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import pl.coderslab.gov_app.interpellation.InterpellationService;
import pl.coderslab.gov_app.role.RoleService;

import javax.validation.Valid;

@Controller
@AllArgsConstructor
public class CouncilmanController {

    CouncilmanService councilmanService;
    RoleService roleService;
    InterpellationService interpellationService;


    @GetMapping("/addcouncilman")
    public String addcouncilam(Model model) {
        model.addAttribute("councilman", new Councilman());
        model.addAttribute("roles", roleService.getAllRole());
        return "Councilman-form-add";
        }

    @PostMapping("/addcouncilman")
    public String addcoucilman(@Valid Councilman councilman, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "Councilman-form-add";
        }
        councilmanService.addCoucilman(councilman);
        return "redirect:/showallcouncilman";
    }

    @GetMapping("/showallcouncilman")
    public String showallcouncilman(Model model){
        model.addAttribute("councilmans", councilmanService.getAllCouncilman());
        return "Councilman-show-all";
    }

    @GetMapping("/showcouncilman/{id}")
    public String showcouncilman(@PathVariable Long id, Model model){
       Councilman councilman = councilmanService.getCouncilman(id).get();
       model.addAttribute("councilman", councilman);
       model.addAttribute("CouncInterp", interpellationService.getInterpellationByCouncilId(id));
       return "Councilman-show-profile";
    }

    //referencja do klucza interpelacje
    @GetMapping("/deletecouncilman/{id}")
    public String deletecouncilman(@PathVariable Long id){
       councilmanService.deleteCouncilman(id);
       return "councilmanAddForm";
    }

    //update do popawienia bo zapisuje na nowo do bazy danych
    @GetMapping("/updatecouncilman/{id}")
    public String updatecouncilman(@PathVariable Long id, Model model){
        model.addAttribute("councilman", councilmanService.getCouncilman(id).get());
        return "Councilman-form-update";
    }



}
