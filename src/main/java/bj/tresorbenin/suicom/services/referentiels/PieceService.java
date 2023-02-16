package bj.tresorbenin.suicom.services.referentiels;

import bj.tresorbenin.suicom.entities.referentiels.Piece;
import bj.tresorbenin.suicom.repositories.jpa.referentiels.PieceRepository;
import bj.tresorbenin.suicom.services.AbstractBaseService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PieceService extends AbstractBaseService<Piece> {

    @Autowired
    @Getter
    private PieceRepository repository;

    public PieceService() {
        super(Piece.class);
    }
}
