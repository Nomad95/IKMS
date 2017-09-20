package pl.politechnika.ikms.rest.controller.person;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.politechnika.ikms.domain.person.AddressEntity;
import pl.politechnika.ikms.rest.dto.person.AddressDto;
import pl.politechnika.ikms.rest.mapper.person.AddressEntityMapper;
import pl.politechnika.ikms.service.person.AddressService;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/address")
@RequiredArgsConstructor
public class AddressController {

    private final @NonNull AddressService addressService;
    private final @NonNull AddressEntityMapper addressEntityMapper;

    @GetMapping(value = "/{addressId}")
    @ResponseBody
    public AddressDto getOneAddress(@PathVariable Long addressId){
        return addressEntityMapper.convertToDto(addressService.findOne(addressId));
    }

    @PostMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public AddressDto createAddress(@Valid @RequestBody AddressDto addressDto){
        AddressEntity addressEntity = addressService.create(addressEntityMapper.convertToEntity(addressDto));
        return addressEntityMapper.convertToDto(addressEntity);
    }

    @GetMapping
    @ResponseBody
    public Page<AddressDto> getAllAddresses(Pageable pageable){
        return addressService.findAllPaginated(pageable).map(addressEntityMapper::convertToDto);
    }

    @PutMapping
    @ResponseBody
    public AddressDto updateAddress(@Valid @RequestBody AddressDto addressDto){
        AddressEntity addressEntity = addressService.update(addressEntityMapper.convertToEntity(addressDto));
        return addressEntityMapper.convertToDto(addressEntity);
    }

    @DeleteMapping(value = "/{addressId}")
    public void deleteAddress(@PathVariable Long addressId){
        addressService.deleteById(addressId);
    }

}
