package br.com.fatecmogidascruzes.factories;

import br.com.fatecmogidascruzes.entities.ServicoVeterinario;

public interface ServicoFactory {
    ServicoVeterinario criarServico(int petId, int veterinarioId, Object... outrosParametros);
}