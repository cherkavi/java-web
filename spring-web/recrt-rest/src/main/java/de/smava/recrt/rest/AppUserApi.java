package de.smava.recrt.rest;

import de.smava.recrt.exception.RecrtServiceException;
import de.smava.recrt.model.AppUser;
import de.smava.recrt.rest.model.AppUserResource;
import de.smava.recrt.service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/rest/users", produces = {APPLICATION_JSON_VALUE})
public class AppUserApi {

    @Autowired
    @Qualifier("appUserService")
    private AppUserService appUserService;

    /*@InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(new AdvisorValidator());
    }*/

    @Secured({"ROLE_ADMIN"})
    @RequestMapping(method = RequestMethod.GET)
    public List<AppUserResource> search() throws RecrtServiceException {

        List<AppUserResource> result = new ArrayList<>();

        List<? extends AppUser> userEntities = appUserService.getAll();
        for (AppUser userEntity : userEntities) {
            result.add(new AppUserResource(userEntity));
        }

        return result;
    }

}
