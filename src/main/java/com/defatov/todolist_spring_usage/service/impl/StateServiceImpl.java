package com.defatov.todolist_spring_usage.service.impl;

import com.defatov.todolist_spring_usage.exceptions.NullEntityReferenceException;
import com.defatov.todolist_spring_usage.model.State;
import com.defatov.todolist_spring_usage.repository.StateRepository;
import com.defatov.todolist_spring_usage.service.StateService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StateServiceImpl implements StateService {

    private final StateRepository stateRepository;

    @Autowired
    public StateServiceImpl(StateRepository stateRepository) {
        this.stateRepository = stateRepository;
    }

    @Override
    public State create(State role) {
        if (role != null) {
            return stateRepository.save(role);
        }
        throw new NullEntityReferenceException("State cannot be 'null'");
    }

    @Override
    public State readById(String id) {
        return stateRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("State with id " + id + " not found"));
    }

    @Override
    public State update(State role) {
        if (role != null) {
            readById(role.getId());
            return stateRepository.save(role);
        }
        throw new NullEntityReferenceException("State cannot be 'null'");
    }

    @Override
    public void delete(String id) {
        stateRepository.delete(readById(id));
    }

    @Override
    public State getByName(String name) {
        Optional<State> optional = Optional.ofNullable(stateRepository.findByName(name));
        if (optional.isPresent()) {
            return optional.get();
        }
        throw new EntityNotFoundException("State with name '" + name + "' not found");
    }

    @Override
    public List<State> getAll() {
        List<State> states = stateRepository.findAll();
        return states.isEmpty() ? new ArrayList<>() : states;
    }

}
