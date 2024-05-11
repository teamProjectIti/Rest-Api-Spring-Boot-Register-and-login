package com.spacek.Shard.helper;

import com.spacek.Payload.ResultResponse.PaginationResult;
import com.spacek.entity.contracts.BaseEntity;
import com.spacek.repositery.BaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.modelmapper.ModelMapper;

import java.util.stream.Collectors;

@Component
public class PaginationMaker {
    @Autowired
    private ModelMapper modelMapper;

    public <T extends BaseEntity, ID extends Number> PaginationResult<T> paginate(int pageNo, int pageSize, String sortBy, String sortDir, Class<T> entityDto, BaseRepository<T, ID> baseRepository) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy)
                .ascending() : Sort.by(sortBy)
                .descending();

        var pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<T> objectList = baseRepository.findAll(pageable);

        var content = objectList.getContent()
                .stream()
                .map(x -> modelMapper.map(x, entityDto))
                .collect(Collectors.toList());

        return new PaginationResult<>(
                content,
                objectList.getNumber(),
                objectList.getSize(),
                objectList.getTotalElements(),
                objectList.getTotalPages(),
                objectList.isLast()
        );
    }
}

