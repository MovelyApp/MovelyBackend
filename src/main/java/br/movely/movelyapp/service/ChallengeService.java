package br.movely.movelyapp.service;

import br.movely.movelyapp.DTO.EditChallengeDTO;
import br.movely.movelyapp.DTO.ResponseChallengeDTO;
import br.movely.movelyapp.model.Challenge;
import br.movely.movelyapp.repository.ChallengeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ChallengeService {

    private final ChallengeRepository repository;
    public ChallengeService(ChallengeRepository repository) {
        this.repository = repository;
    }
    public Page<Challenge> list(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Challenge find(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Challenge not found"));
    }

    public Challenge save(Challenge challenge) {
        return repository.save(challenge);
    }

    public ResponseChallengeDTO edit(UUID id, EditChallengeDTO dto) {
        Challenge challenge = find(id);

        if (dto.getName() != null) {
            challenge.setName(dto.getName());
        }
        if (dto.getDescription() != null) {
            challenge.setDescription(dto.getDescription());
        }
        if (dto.getDataInicio() != null) {
            challenge.setDataInicio(dto.getDataInicio());
        }
        if (dto.getDataFim() != null) {
            challenge.setDataFim(dto.getDataFim());
        }

        challenge = repository.save(challenge);
        return ResponseChallengeDTO.toDTO(challenge);
    }

    public void encerrar(UUID id) {
        Challenge challenge = find(id);
        challenge.encerrarDesafio();
        repository.save(challenge);
    }
}