package br.ceub.agendaacademica

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class ResultadoActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Recebe os dados enviados pelo Intent
        val disciplina = intent.getStringExtra("disciplina") ?: ""
        val atividade = intent.getStringExtra("atividade") ?: ""
        val dias = intent.getIntExtra("dias", 0)
        val valor = intent.getDoubleExtra("valor", 0.0)
        val concluida = intent.getBooleanExtra("concluida", false)
        val situacao = intent.getStringExtra("situacao") ?: ""
        val prioridade = intent.getStringExtra("prioridade") ?: ""

        setContent {
            MaterialTheme {
                TelaResultado(disciplina, atividade, dias, valor, concluida, situacao, prioridade) {
                    finish()
                }
            }
        }
    }
}

@Composable
fun TelaResultado(
    disciplina: String,
    atividade: String,
    dias: Int,
    valor: Double,
    concluida: Boolean,
    situacao: String,
    prioridade: String,
    onVoltar: () -> Unit
) {
    val corSituacao = when (situacao) {
        "CONCLUÍDA" -> Color(0xFF2E7D32)
        "PRAZO ENCERRADO" -> Color(0xFF616161)
        "URGENTE" -> Color(0xFFC62828)
        "ATENÇÃO" -> Color(0xFFEF6C00)
        else -> Color(0xFF1565C0)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFF8E1)) // cor de fundo personalizada
            .statusBarsPadding()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_academico),
            contentDescription = "Ícone acadêmico",
            modifier = Modifier.height(80.dp)
        )
        Text(
            text = "RESULTADO DA ANÁLISE",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1A237E)
        )
        Spacer(modifier = Modifier.height(20.dp))

        Text(text = "Situação", color = Color.DarkGray)
        Text(
            text = situacao,
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = corSituacao
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(text = "Prioridade", color = Color.DarkGray)
        Text(
            text = prioridade,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1A237E)
        )

        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = "Resumo da atividade",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text("Disciplina: $disciplina", modifier = Modifier.fillMaxWidth())
        Text("Atividade: $atividade", modifier = Modifier.fillMaxWidth())
        Text("Dias até a entrega: $dias", modifier = Modifier.fillMaxWidth())
        Text("Valor: $valor pontos", modifier = Modifier.fillMaxWidth())
        Text(
            "Concluída: " + if (concluida) "Sim" else "Não",
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))
        Button(modifier = Modifier.fillMaxWidth(), onClick = onVoltar) {
            Text("VOLTAR")
        }
    }
}
