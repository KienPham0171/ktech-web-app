package com.kein.ktech.service.impl;

import com.kein.ktech.repository.StatsRepository;
import com.kein.ktech.service.StatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class StatServiceImpl implements StatsService {
    @Autowired
    StatsRepository repo;
    @Override
    public List<Object[]> catStats() {
        return repo.catStats();
    }
}
