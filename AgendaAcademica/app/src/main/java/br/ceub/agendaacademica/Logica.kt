package br.ceub.agendaacademica

/**
 * Regra 1 - Situação da atividade.
 * A ORDEM das verificações importa: do mais específico para o mais abrangente.
 */
fun calcularSituacao(dias: Int, concluida: Boolean): String {
    return if (concluida) {
        "CONCLUÍDA"
    } else if (dias <= 0) {
        "PRAZO ENCERRADO"
    } else if (dias <= 3) {
        "URGENTE"
    } else if (dias <= 7) {
        "ATENÇÃO"
    } else {
        "NO PRAZO"
    }
}

/**
 * Regra 2 - Prioridade (combina duas condições com &&).
 */
fun calcularPrioridade(dias: Int, valor: Double): String {
    return if (dias <= 3 && valor >= 20.0) {
        "PRIORIDADE ALTA"
    } else if (dias <= 7) {
        "PRIORIDADE MÉDIA"
    } else {
        "PRIORIDADE BAIXA"
    }
}
