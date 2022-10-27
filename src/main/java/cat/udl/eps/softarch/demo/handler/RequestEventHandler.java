package cat.udl.eps.softarch.demo.handler;

import cat.udl.eps.softarch.demo.domain.Request;
import cat.udl.eps.softarch.demo.domain.User;
import cat.udl.eps.softarch.demo.exception.ForbiddenException;
import cat.udl.eps.softarch.demo.exception.UnauthorizedException;
import cat.udl.eps.softarch.demo.repository.RequestRepository;
import org.springframework.data.rest.core.annotation.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;


import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
@RepositoryEventHandler
public class RequestEventHandler {


    final RequestRepository requestRepository;

    public RequestEventHandler(RequestRepository requestRepository) {
        this.requestRepository = requestRepository;
    }


    //Esto para evitar que se puedan modificar entidades.
    @HandleBeforeSave
    public void handleRequestPreSave(Request newRequest) {
        assert newRequest.getId() != null;
        Optional<Request> requests = requestRepository.findById(newRequest.getId());

        if(requests.isPresent()){
            Request oldRequest = requests.get();
            if(differentAtts(oldRequest, newRequest)) {
                throw new ForbiddenException();
            }
        }
    }

    //TODO: si no funciona, deberíamos redefinir el equals. Supuestamente String ya tiene uno que funciona, y BigDecimal tambien. El poblemo es comparar users.
    private boolean differentAtts(Request oldRequest, Request newRequest) {
        return  !oldRequest.getName().equals(newRequest.getName()) &&
                !oldRequest.getDescription().equals(newRequest.getDescription()) &&
                !oldRequest.getPrice().equals(newRequest.getPrice()) &&
                !oldRequest.getRequester().equals(newRequest.getRequester());
    }

    @HandleBeforeCreate
    public void handleRequestPreCreate(Request request) {
        assert request.getId() != null;

        String name = request.getName();
        BigDecimal price = request.getPrice();
        String description = request.getDescription();
        User requester = request.getRequester();
        List<Request> requests = requestRepository.findByNameAndPriceAndDescriptionAndRequester(name, price, description, requester);

        //TODO: remember que hay algo de fechas


        if(!requests.isEmpty()) {
            throw new ForbiddenException();
        }
    }

    @HandleBeforeDelete
    public void handleRequestPreDelete(Request request){
        assert request.getId() != null;

        List<Request> requestList = requestRepository.findByRequester(request.getRequester());

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }

        if(!Objects.equals(username, request.getRequester().getUsername())){
            throw new ForbiddenException();
        }

        if(!requestList.contains(request)){
            throw new ForbiddenException();
        }
    }

}
