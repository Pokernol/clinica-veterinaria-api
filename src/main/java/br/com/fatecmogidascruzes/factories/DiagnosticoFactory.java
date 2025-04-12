package br.com.fatecmogidascruzes.factories;

import br.com.fatecmogidascruzes.entities.Diagnostico;
import br.com.fatecmogidascruzes.builders.DiagnosticoBuilder;

public class DiagnosticoFactory extends BaseFactory<Diagnostico> {

    @Override
    public Diagnostico create() {
        return new DiagnosticoBuilder()
                .withDescricao("Infecção bacteriana")
                .withGravidade("Moderada")
                .build();
    }
}