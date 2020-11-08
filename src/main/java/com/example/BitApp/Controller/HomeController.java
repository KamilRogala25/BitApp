package com.example.BitApp.Controller;


import com.example.BitApp.Exceptions.DirectoryIsEmptyExcepion;
import com.example.BitApp.Exceptions.SelectionIsEmptyException;
import com.example.BitApp.Model.FileModifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

@Controller
public class HomeController {

    @GetMapping("/")
    public String getHomePage(ModelMap map) {
        return "home";
    }

    @GetMapping("/results")
    public String getResults(@RequestParam(value = "path") String path, @RequestParam(value = "extension") String extension,
                             @RequestParam(value = "toFindSequ") CharSequence toFindSequ, @RequestParam(value = "toReplaceSequ") CharSequence toReplaceSequ, ModelMap map) throws SelectionIsEmptyException, IOException, DirectoryIsEmptyExcepion {
        FileModifier fileModifier = new FileModifier();
        map.put("results", fileModifier.modifyFiles(path, extension, toFindSequ, toReplaceSequ));
        return "results";
    }

}
