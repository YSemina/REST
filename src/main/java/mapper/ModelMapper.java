package mapper;

public interface ModelMapper<E, D> {
    D toDto(E incomingDto);
    E toEntity(D outGoingDto);
}
