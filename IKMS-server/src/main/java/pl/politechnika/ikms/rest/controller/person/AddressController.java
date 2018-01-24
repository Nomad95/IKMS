package pl.politechnika.ikms.rest.controller.person;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.politechnika.ikms.rest.dto.person.AddressDto;
import pl.politechnika.ikms.service.person.AddressService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/api/address")
@RequiredArgsConstructor
public class AddressController {

    private final @NonNull AddressService addressService;

    @GetMapping(value = "/{addressId}")
    public AddressDto getOneAddress(@PathVariable Long addressId){
        return addressService.findOne(addressId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AddressDto createAddress(@Valid @RequestBody AddressDto addressDto){
        return addressService.create(addressDto);
    }

    @GetMapping
    public Page<AddressDto> getAllAddresses(Pageable pageable){
        return addressService.findAllPaginated(pageable);
    }

    @PutMapping
    public AddressDto updateAddress(@Valid @RequestBody AddressDto addressDto){
        return addressService.update(addressDto);
    }

    @DeleteMapping(value = "/{addressId}")
    public void deleteAddress(@PathVariable Long addressId){
        addressService.deleteById(addressId);
    }

    @GetMapping(value = "/personalData/{personalDataId}")
    public List<AddressDto> getAddressesByPersonalDataId(@PathVariable Long personalDataId){
        return addressService.findByPersonalDataId(personalDataId);
    }

    @GetMapping(value = "/parent/{parentId}")
    public List<AddressDto> getAddressesByParentId(@PathVariable Long parentId){
        return addressService.findByParentId(parentId);
    }

}
