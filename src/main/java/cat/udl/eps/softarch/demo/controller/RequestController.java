package cat.udl.eps.softarch.demo.controller;

import cat.udl.eps.softarch.demo.domain.Request;
import cat.udl.eps.softarch.demo.domain.User;
import cat.udl.eps.softarch.demo.exception.UnauthorizedException;
import cat.udl.eps.softarch.demo.repository.RequestRepository;
import cat.udl.eps.softarch.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.BasePathAwareController;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


//@RestController
@BasePathAwareController
public class RequestController {

    @Autowired
    private RequestRepository requestRepository;

    @Autowired
    private UserRepository userRepository;


    @GetMapping("/requests")
    public @ResponseBody List<Request> getUserOwnRequests() {
//        Iterable<Request> requestIterable = requestRepository.findAll();
        //      List<Request> requestList = new ArrayList<>();
        //    requestIterable.forEach(requestList::add);
        ///////////////
        User currentUser = getCurrentUser();
        return requestRepository.findByRequester(currentUser);
    }

//    @GetMapping("/requests")
//    public @ResponseBody List<Request> getOtherUserOwnRequests(@RequestParam String username) {
////        Iterable<Request> requestIterable = requestRepository.findAll();
//        //      List<Request> requestList = new ArrayList<>();
//        //    requestIterable.forEach(requestList::add);
//        ///////////////
//        System.out.println(username);
//        Optional<User> users = userRepository.findById(username);
//        //TODO: cuidao si el nuevo username no existe
//        User user = users.orElseGet(User::new);
//        return requestRepository.findByRequester(user);
//    }

    public User getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }
        if(username.equals("anonymous")) {
            throw new UnauthorizedException();
        }
        Optional<User> currentUser = userRepository.findById(username);
        System.out.println(username);
        return currentUser.orElseGet(User::new);
    }


}
