package zhitu.controller;

import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin
public class UserController {

    @RequestMapping(value = "/",  method = RequestMethod.GET)
    public String index(
            @RequestParam(value="name") String name,
            @RequestParam(value="age") int age
    ) {
        return String.format("name = %s\tage = %d", name, age);
    }

}
