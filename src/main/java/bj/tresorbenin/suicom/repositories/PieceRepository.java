package bj.tresorbenin.suicom.repositories;


import bj.tresorbenin.suicom.entities.Piece;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PieceRepository extends JpaRepository<Piece, Long> {
}
