package bj.tresorbenin.suicom.repositories;


import bj.tresorbenin.suicom.entities.Piece;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PieceRepository extends JpaRepository<Piece, Long> {
}
