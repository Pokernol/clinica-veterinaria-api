package br.com.fatecmogidascruzes.factories;

import br.com.fatecmogidascruzes.entities.Consulta;
import br.com.fatecmogidascruzes.builders.ConsultaBuilder;
import java.time.LocalDateTime;

public class ConsultaFactory extends BaseFactory<Consulta> {

    @Override
    public Consulta create() {
        return new ConsultaBuilder()
                .withDataHora(LocalDateTime.now())
                .withVeterinario("Dra. Maria")
                .withObservacoes("Animal apresentou febre e apatia.")
                .build();
    }
}