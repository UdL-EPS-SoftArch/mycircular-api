package cat.udl.eps.softarch.demo.controller;

import cat.udl.eps.softarch.demo.domain.Request;
import cat.udl.eps.softarch.demo.domain.User;
import cat.udl.eps.softarch.demo.exception.NotFoundException;
import cat.udl.eps.softarch.demo.exception.UnauthorizedException;
import cat.udl.eps.softarch.demo.repository.RequestRepository;
import cat.udl.eps.softarch.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.BasePathAwareController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;


//@RestController
@BasePathAwareController
public class RequestController {

    @Autowired
    private RequestRepository requestRepository;

    @Autowired
    private UserRepository userRepository;


//    @GetMapping("/requests")
//    public @ResponseBody List<Request> getUserOwnRequests() {
////        Iterable<Request> requestIterable = requestRepository.findAll();
//        //      List<Request> requestList = new ArrayList<>();
//        //    requestIterable.forEach(requestList::add);
//        ///////////////
//        User currentUser = getCurrentUser();
//        return requestRepository.findByRequester(currentUser);
//    }

    @Transactional
//    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/requests")
    public @ResponseBody ResponseEntity<String> deleteUserRequests(@RequestParam(value = "username", required = false) String username) {
        //aqui ya checkeamos que no pueda ser anonymous
        User currentUser = getCurrentUser();
        //si estamos buscando al usuario actual
        if (username.equals(currentUser.getUsername())) {
            //borramos las requests del usuario actual
            //return requestRepository.findByRequester(currentUser);
            System.out.println("usuario: " + currentUser.getUsername());
            requestRepository.deleteByRequester(currentUser);
            return new ResponseEntity<>(username, HttpStatus.NO_CONTENT);
        }

        //ojo, aqui no hay que buscar a otro usuario, porque solo podemos eliminar nuetras propias requests
        Optional<User> users = userRepository.findById(username);
        //miramos que exista el otro usuario
        if (users.isEmpty()) {
            return new ResponseEntity<>(username, HttpStatus.NOT_FOUND);
        }
        //si existe el usuario... pues no le dejamos tocar nada suyo.
        return new ResponseEntity<>(username, HttpStatus.FORBIDDEN);
    }

    /*
    @Transactional
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @DeleteMapping("/requests")
    public @ResponseBody ResponseEntity<String> deleteOtherUserRequests(@RequestParam(value = "username", required = false) String username) {
        User currentUser = getCurrentUser();
        //si estamos buscando al usuario actual
        if (!username.equals(currentUser.getUsername())) {
            return new ResponseEntity<>(username, HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(username, HttpStatus.OK);//este OK es de prueba
    }
    */

//Todo: poner aqui tambien el response entity en lugar de las excepciones.
    @GetMapping("/requests")
    public @ResponseBody List<Request> getOtherUserOwnRequests(@RequestParam(value = "username", required = false) String username) {
//        System.out.println(username);
        //aqui ya checkeamos que no pueda ser anonymous
        User currentUser = getCurrentUser();
        //si estamos buscando al usuario actual
        if (username.equals(currentUser.getUsername())) {
            return requestRepository.findByRequester(currentUser);
        }
        //buscamos a otro usuario
        Optional<User> users = userRepository.findById(username);
        //miramos que exista el otro usuario
        if (users.isEmpty()) {
            throw new NotFoundException();
        }
        User otherUser = users.get();
        return requestRepository.findByRequester(otherUser);


    }

    public User getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }
        if (username.equals("anonymous")) {
            throw new UnauthorizedException();
        }
        Optional<User> currentUser = userRepository.findById(username);
        System.out.println(username);
        return currentUser.orElseGet(User::new);
    }


}
