package br.com.fatecmogidascruzes.factories;

import br.com.fatecmogidascruzes.builders.ConsultaBuilder;
import br.com.fatecmogidascruzes.entities.ServicoVeterinario;
import java.time.LocalDateTime;

public class ConsultaFactory implements ServicoFactory {

    @Override
    public ServicoVeterinario criarServico(int petId, int veterinarioId, Object... outrosParametros) {
        if (outrosParametros.length < 2 || !(outrosParametros[0] instanceof LocalDateTime) || !(outrosParametros[1] instanceof String)) {
            throw new IllegalArgumentException("Parâmetros inválidos para criar Consulta. Esperado: LocalDateTime dataHora, String motivoConsulta.");
        }
        LocalDateTime dataHora = (LocalDateTime) outrosParametros[0];
        String motivoConsulta = (String) outrosParametros[1];


        return new ConsultaBuilder()
                .withPetId(petId)
                .withVeterinarioId(veterinarioId)
                .withDataHora(dataHora)
                .withMotivoConsulta(motivoConsulta)
                .build();
    }
}