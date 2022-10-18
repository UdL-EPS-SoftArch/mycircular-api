package cat.udl.eps.softarch.demo.handler;

import cat.udl.eps.softarch.demo.domain.Request;
import cat.udl.eps.softarch.demo.domain.User;
import cat.udl.eps.softarch.demo.exception.ForbiddenException;
import cat.udl.eps.softarch.demo.exception.UnauthorizedException;
import cat.udl.eps.softarch.demo.repository.RequestRepository;
import org.springframework.data.rest.core.annotation.Description;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
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
//        if(requestRepository.existsById(request.getId())){
//            throw new ForbiddenException();
//        }
        String name = request.getName();
        BigDecimal price = request.getPrice();
        String description = request.getDescription();
        User requester = request.getRequester();
        List<Request> requests = requestRepository.findByNameAndPriceAndDescriptionAndRequester(name, price, description, requester);

//        List<Request> pruebaNamePrice = requestRepository.findByNameAndPrice(name, price);
//        System.out.println(pruebaNamePrice.size());
//TODO: remember que hay algo de fechas


//        List<Request> pruebaNamePriceDescription = requestRepository.findByNameAndPriceAndDescription(name, price, description);
//        System.out.println(pruebaNamePriceDescription.size());
//
//        System.out.println(requests.size());

        if(!requests.isEmpty()) {
            throw new ForbiddenException();
        }

//        System.out.println("CACAAAAA123");
//
//        List<Request> requestsName = requestRepository.findByName(name);
//        List<Request> requestsPrice = requestRepository.findByPrice(price);
//        List<Request> requestsDescription = requestRepository.findByDescription(description);
//        List<Request> requestsRequester = requestRepository.findByRequester(requester);
//        System.out.println(requestsName.size());
//        System.out.println(requestsPrice.size());
//        System.out.println(requestsDescription.size());
//        System.out.println(requestsRequester.size());
//        Boolean mirar = requestsName.contains(request) == requestsPrice.contains(request) == requestsDescription.contains(request) && requestsRequester.contains(request);
//        System.out.println(mirar);
//        System.out.println(requestsName.contains(request));

//        System.out.println(requests);
//        Boolean comprobar = requests.contains(request);
//        System.out.println(comprobar);
//        System.out.println(requests.size());
//        System.out.println(request);
//        System.out.println(request.getName());
//        System.out.println(requests.get(0).getName());
//        System.out.println(request.getPrice());
//        System.out.println(requests.get(0).getPrice());
//        System.out.println(request.getDescription());
//        System.out.println(requests.get(0).getDescription());
//        System.out.println(request.getRequester().getUsername());
//        System.out.println(requests.get(0).getRequester().getUsername());

//        if(requests.contains(request)) {
//            System.out.println("CACAAAAA");
//            throw new UnauthorizedException();
//        }
    }

}
