package bj.tresorbenin.suicom.services;

import bj.tresorbenin.suicom.entities.Demande;
import bj.tresorbenin.suicom.repositories.jpa.DemandeRepository;
import bj.tresorbenin.suicom.utils.JavaUtils;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class DemandeService extends AbstractBaseService<Demande> {

    @Autowired
    @Getter
    private DemandeRepository repository;

    public DemandeService() {
        super(Demande.class);
    }

    public List<Demande> searchByPeriod(String dateDebut, String dateFin){
        List<Demande> listFiltree = null;
        Map<String, String> periode = JavaUtils.getSearchPeriode(dateDebut, dateFin);

        Date dateDebute=JavaUtils.stringIntoDateWithFormat(periode.get("dateDebut"),"yyyy-MM-dd");
        Date dateFine=JavaUtils.stringIntoDateWithFormat(periode.get("dateFin"),"yyyy-MM-dd");
        return toList(repository.getByPeriod(dateDebute,dateFine));
    }


}
