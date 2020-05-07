package com.bshop.vendor;

import com.bshop.address.model.AddressRepository;
import com.bshop.contactInfo.model.ContactInfoRepository;
import com.bshop.vendor.assembler.VendorAssembler;
import com.bshop.vendor.dto.VendorFull;
import com.bshop.vendor.model.Vendor;
import com.bshop.vendor.model.VendorRepository;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/vendor")
public class VendorController {
    private SessionFactory sessionFactory;
    private VendorRepository vendorRepository;
    private ContactInfoRepository contactInfoRepository;
    private AddressRepository addressRepository;

    private VendorAssembler vendorAssembler = new VendorAssembler();

    public VendorController(SessionFactory sessionFactory, VendorRepository vendorRepository, ContactInfoRepository contactInfoRepository, AddressRepository addressRepository) {
        this.sessionFactory = sessionFactory;
        this.vendorRepository = vendorRepository;
        this.contactInfoRepository = contactInfoRepository;
        this.addressRepository = addressRepository;
    }

    @RequestMapping(path = "/{vendorId}", method = RequestMethod.GET)
    @Transactional(propagation= Propagation.REQUIRED, readOnly=true, noRollbackFor=Exception.class)
    public VendorFull getVendor(@PathVariable Integer vendorId) {
        VendorFull result = null;

        Optional<Vendor> vendorOptional = vendorRepository.findById(vendorId);
        if (vendorOptional.isPresent()) {
            result = vendorAssembler.assembleFullDto(vendorOptional.get());
        }
        return result;
    }

    // TODO: change return result
    // TODO: Validation logic
    @RequestMapping(path = "/add", method = RequestMethod.POST)
    public void addNewVendors(@RequestBody List<VendorFull> vendorsToAdd) {
        List<Vendor> newVendors = vendorsToAdd.stream().map(dto -> vendorAssembler.assembleModel(dto)).collect(Collectors.toList());

        for (Vendor vendor: newVendors) {
            contactInfoRepository.saveAll(vendor.getContats());
            addressRepository.saveAll(vendor.getAddresses());
        }

        vendorRepository.saveAll(newVendors);
    }

}
