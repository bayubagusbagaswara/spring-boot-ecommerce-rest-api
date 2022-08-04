package com.ecommerce.service.impl;

import com.ecommerce.entity.region.District;
import com.ecommerce.entity.region.SubDistrict;
import com.ecommerce.dto.kecamatan.*;
import com.ecommerce.dto.region.subDistrict.*;
import com.ecommerce.exception.SubDistrictNotFoundException;
import com.ecommerce.exception.DistrictNotFoundException;
import com.ecommerce.repository.SubDistrictRepository;
import com.ecommerce.repository.DistrictRepository;
import com.ecommerce.service.region.SubDistrictService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class SubDistrictServiceImpl implements SubDistrictService {

    private final SubDistrictRepository subDistrictRepository;
    private final DistrictRepository districtRepository;
    private final RegionMapper regionMapper;

    public SubDistrictServiceImpl(SubDistrictRepository subDistrictRepository, DistrictRepository districtRepository, RegionMapper regionMapper) {
        this.subDistrictRepository = subDistrictRepository;
        this.districtRepository = districtRepository;
        this.regionMapper = regionMapper;
    }

    @Override
    public SubDistrictDTO createKecamatan(CreateSubDistrictRequestDTO createKecamatanRequest) throws DistrictNotFoundException {
        District district = districtRepository.findById(createKecamatanRequest.getKotaId())
                .orElseThrow(() -> new DistrictNotFoundException("Kota ID [" + createKecamatanRequest.getKotaId() + "] not found"));
        SubDistrict subDistrict = new SubDistrict();
        subDistrict.setCode(createKecamatanRequest.getCode());
        subDistrict.setName(createKecamatanRequest.getName());
        subDistrict.setDistrict(district);
        subDistrict.setCreatedDate(LocalDateTime.now());
        subDistrictRepository.save(subDistrict);
        return regionMapper.mapToKecamatanResponse(subDistrict);
    }

    @Override
    public SubDistrictDTO getKecamatanById(String id) throws SubDistrictNotFoundException {
        SubDistrict subDistrict = subDistrictRepository.findById(id)
                .orElseThrow(() -> new SubDistrictNotFoundException("Kecamatan ID [" + id + "] not found"));
        return regionMapper.mapToKecamatanResponse(subDistrict);
    }

    @Override
    public SubDistrictDTO updateKecamatan(String id, UpdateSubDistrictRequestDTO updateKecamatanRequest) throws DistrictNotFoundException, SubDistrictNotFoundException {
        District district = districtRepository.findById(updateKecamatanRequest.getKotaId())
                .orElseThrow(() -> new DistrictNotFoundException("Kota ID [" + updateKecamatanRequest.getKotaId() + "] not found"));
        SubDistrict subDistrict = subDistrictRepository.findById(id)
                .orElseThrow(() -> new SubDistrictNotFoundException("Kecamatan ID [" + id + "] not found"));
        subDistrict.setCode(updateKecamatanRequest.getCode());
        subDistrict.setName(updateKecamatanRequest.getName());
        subDistrict.setDistrict(district);
        subDistrict.setUpdatedDate(LocalDateTime.now());
        subDistrictRepository.save(subDistrict);
        return regionMapper.mapToKecamatanResponse(subDistrict);
    }

    @Override
    public void deleteKecamatan(String id) throws SubDistrictNotFoundException {
        final SubDistrict subDistrict = subDistrictRepository.findById(id)
                .orElseThrow(() -> new SubDistrictNotFoundException("Kecamatan ID [" + id + "] not found"));
        subDistrictRepository.delete(subDistrict);
    }

    @Override
    public ListSubDistrictResponseDTO getAllKecamatan(ListSubDistrictRequestDTO listKecamatanRequest) {
        Integer pageNo = listKecamatanRequest.getPageNo();
        Integer pageSize = listKecamatanRequest.getPageSize();
        String sortBy = listKecamatanRequest.getSortBy();
        String sortDir = listKecamatanRequest.getSortDir();

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<SubDistrict> kecamatanPage = subDistrictRepository.findAll(pageable);
        List<SubDistrict> subDistrictList = kecamatanPage.getContent();

        List<SubDistrictDTO> kecamatanResponseList = regionMapper.mapToKecamatanResponseList(subDistrictList);

        ListSubDistrictResponseDTO listKecamatanResponse = new ListSubDistrictResponseDTO();
        listKecamatanResponse.setKecamatanList(kecamatanResponseList);
        listKecamatanResponse.setPageNo(kecamatanPage.getNumber());
        listKecamatanResponse.setPageSize(kecamatanPage.getSize());
        listKecamatanResponse.setTotalElements(kecamatanPage.getTotalElements());
        listKecamatanResponse.setTotalPages(kecamatanPage.getTotalPages());
        listKecamatanResponse.setLast(kecamatanPage.isLast());
        return listKecamatanResponse;
    }

    @Override
    public SubDistrictDTO getKecamatanByName(String name) throws SubDistrictNotFoundException {
        SubDistrict subDistrict = subDistrictRepository.findAllByNameIgnoreCase(name).orElseThrow(() -> new SubDistrictNotFoundException("Kecamatan name [" + name + "] not found"));
        return regionMapper.mapToKecamatanResponse(subDistrict);
    }

    @Override
    public SubDistrictDTO getKecamatanByCode(String code) throws SubDistrictNotFoundException {
        SubDistrict subDistrict = subDistrictRepository.findAllByCode(code).orElseThrow(() -> new SubDistrictNotFoundException("Kecamatan code [" + code + "] not found"));
        return regionMapper.mapToKecamatanResponse(subDistrict);
    }

    @Override
    public List<SubDistrictDTO> getKecamatanByNameContains(String name) {
        List<SubDistrict> subDistrictList = subDistrictRepository.findAllByNameContainingIgnoreCase(name);
        return regionMapper.mapToKecamatanResponseList(subDistrictList);
    }

    @Override
    public List<SubDistrictDTO> getKecamatanByKotaId(String kotaId) {
        List<SubDistrict> subDistrictList = subDistrictRepository.findAllByKotaId(kotaId);
        return regionMapper.mapToKecamatanResponseList(subDistrictList);
    }
}
