package br.movely.movelyapp.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "groups")
@Setter
@Getter
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;
    private String description;
    private String urlImagem;
    private LocalDateTime dataLancamento = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    @ManyToMany
    @JoinTable(
            name = "group_user",
            joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> users = new ArrayList<>();

    public Group() {
    }

    public Group(String name, String description, String urlImagem){
        this.name = name;
        this.description = description;
        this.urlImagem = urlImagem;
        this.dataLancamento = LocalDateTime.now();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrlImagem() {
        return urlImagem;
    }

    public void setUrlImagem(String urlImagem) {
        this.urlImagem = urlImagem;
    }

    public LocalDateTime getDataLancamento() {
        return dataLancamento;
    }

    public void setDataLancamento(LocalDateTime dataLancamento) {
        this.dataLancamento = dataLancamento;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public void atualizarInfo(String name, String description, String urlImagem) {
        if (name != null) {
            this.name = name;
        }
        if (description != null) {
            this.description = description;
        }
        if (urlImagem != null) {
            this.urlImagem = urlImagem;
        }
    }

    public void addUser(User user) {
        if (user == null) {
            return;
        }
        boolean alreadyInGroup = this.users.stream()
                .anyMatch(member -> user.getId() != null && user.getId().equals(member.getId()));
        if (alreadyInGroup) {
            return;
        }
        this.users.add(user);
    }

    public void removeUser(User user) {
        this.users.remove(user);
    }
}
