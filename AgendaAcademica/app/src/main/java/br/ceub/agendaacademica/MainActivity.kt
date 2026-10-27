package br.ceub.agendaacademica

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                TelaCadastro()
            }
        }
    }
}

@Composable
fun TelaCadastro() {
    val context = LocalContext.current

    // Estados da tela (o TextField sempre entrega String)
    var disciplina by remember { mutableStateOf("") }
    var atividade by remember { mutableStateOf("") }
    var dias by remember { mutableStateOf("") }
    var valor by remember { mutableStateOf("") }
    var concluida by remember { mutableStateOf(false) }
    var mensagemErro by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE3F2FD)) // cor de fundo personalizada
            .statusBarsPadding()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_academico), // imagem em res/drawable
            contentDescription = "Ícone acadêmico",
            modifier = Modifier.height(80.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "AGENDA ACADÊMICA",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1A237E)
        )
        Text(text = "Cadastro da atividade", color = Color.DarkGray)
        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = disciplina,
            onValueChange = { disciplina = it },
            label = { Text("Disciplina") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = atividade,
            onValueChange = { atividade = it },
            label = { Text("Atividade") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = dias,
            onValueChange = { dias = it },
            label = { Text("Dias até a entrega") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = valor,
            onValueChange = { valor = it },
            label = { Text("Valor da atividade") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Checkbox(checked = concluida, onCheckedChange = { concluida = it })
            Text("Atividade concluída")
        }

        if (mensagemErro.isNotEmpty()) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = mensagemErro,
                color = Color(0xFFC62828),
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                // VALIDAÇÃO: conversão segura (não quebra com "abc")
                val diasNumero: Int? = dias.trim().toIntOrNull()
                val valorNumero: Double? = valor.trim().replace(",", ".").toDoubleOrNull()

                if (disciplina.isBlank() || atividade.isBlank()) {
                    mensagemErro = "Preencha a disciplina e a atividade."
                } else if (diasNumero == null || valorNumero == null || valorNumero < 0) {
                    mensagemErro = "Dados inválidos"
                } else {
                    mensagemErro = ""

                    // PROCESSAMENTO: situação e prioridade calculadas aqui
                    val situacao = calcularSituacao(diasNumero, concluida)
                    val prioridade = calcularPrioridade(diasNumero, valorNumero)

                    // NAVEGAÇÃO: Intent explícito + passagem de dados
                    val intent = Intent(context, ResultadoActivity::class.java)
                    intent.putExtra("disciplina", disciplina.trim())
                    intent.putExtra("atividade", atividade.trim())
                    intent.putExtra("dias", diasNumero)
                    intent.putExtra("valor", valorNumero)
                    intent.putExtra("concluida", concluida)
                    intent.putExtra("situacao", situacao)
                    intent.putExtra("prioridade", prioridade)
                    context.startActivity(intent)
                }
            }
        ) {
            Text("ANALISAR ATIVIDADE")
        }
    }
}
