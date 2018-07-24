package zhitu.sq.dataset.controller;

import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin
public class ProjectController {

    @RequestMapping(value = "/",  method = RequestMethod.GET)
    public String index(
            @RequestParam(value="name") String name,
            @RequestParam(value="age") int age
    ) {
    	System.out.println("aaa");
        return String.format("name = %s\tage = %d", name, age);
    }

}
