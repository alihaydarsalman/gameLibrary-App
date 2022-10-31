package com.turkcell.gamelibrary.system.core.mapping;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ModelMapperServiceImpl implements ModelMapperService {

    private ModelMapper modelMapper;

    @Autowired
    public ModelMapperServiceImpl(ModelMapper modelMapper){
        this.modelMapper = modelMapper;
    }

    @Override
    public ModelMapper forDto() {
        this.modelMapper.getConfiguration().
                setAmbiguityIgnored(true).
                setMatchingStrategy(MatchingStrategies.LOOSE);
        return modelMapper;
    }

    @Override
    public ModelMapper forRequest() {
        this.modelMapper.getConfiguration().
                setAmbiguityIgnored(true).
                setMatchingStrategy(MatchingStrategies.STANDARD);
        return modelMapper;
    }
}
