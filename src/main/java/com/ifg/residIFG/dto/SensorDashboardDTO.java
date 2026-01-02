package com.ifg.residIFG.dto;

import com.ifg.residIFG.domain.piscinas.LeituraSensor;

public record SensorDashboardDTO(
        LeituraSensor leitura,
        Double volumeLitros // Vamos mandar já o volume calculado ou as dimensões
) {}