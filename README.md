# SensorsWatch8 📱⌚

## Descrição

O **Sensors Watch** é um aplicativo Android Wear OS desenvolvido para testar e monitorar os sensores disponíveis em relógios inteligentes. O projeto tem como objetivo apresentar todos os sensores disponíveis no dispositivo, mostrar seus dados em tempo real e exibir o status de funcionamento de cada sensor.

## 🎯 Objetivo

O aplicativo foi criado para:
- **Identificar sensores disponíveis** no relógio Wear OS
- **Monitorar dados em tempo real** dos sensores ativos
- **Exibir status de funcionamento** de cada sensor
- **Testar funcionalidade** dos sensores do dispositivo
- **Fornecer interface intuitiva** para visualização dos dados

## 🏗️ Arquitetura

O projeto segue a arquitetura MVVM (Model-View-ViewModel) com Jetpack Compose:

### Estrutura do Projeto
```
app/src/main/java/com/davi/sensorswatch/
├── MainActivity.kt                    # Activity principal
├── presentation/
│   ├── SensorTestScreen.kt           # Tela principal (UI)
│   ├── theme/
│   │   └── Theme.kt                  # Tema do aplicativo
│   └── viewModels/
│       └── SensorTestViewModel.kt     # ViewModel principal
└── services/
    └── WearSensorTestService.kt      # Serviço de sensores
```

## 🛠️ Tecnologias Utilizadas

### Core Technologies
- **Kotlin** - Linguagem principal do projeto
- **Android SDK** - Plataforma base
- **Wear OS SDK** - SDK específico para relógios inteligentes

### UI & Framework
- **Jetpack Compose** - Framework de UI declarativo
- **Wear Compose Material** - Componentes específicos para Wear OS
- **Material Design** - Design system do Google

### Arquitetura & Dependências
- **ViewModel** - Gerenciamento de estado da UI
- **StateFlow** - Fluxo de dados reativo
- **Coroutines** - Programação assíncrona
- **Lifecycle Components** - Gerenciamento de ciclo de vida

### Versões Principais
- **Android Gradle Plugin**: 8.12.0-alpha01
- **Kotlin**: 2.0.21
- **Compose BOM**: 2024.09.00
- **Min SDK**: 33 (Android 13)
- **Target SDK**: 36 (Android 15)

## 📱 Funcionalidades

### Interface Principal
- **Tela de teste de sensores** com interface otimizada para relógios
- **Botões de controle** para iniciar/parar monitoramento
- **Cards informativos** para cada sensor disponível

### Monitoramento de Sensores
- **Detecção automática** de todos os sensores disponíveis
- **Status em tempo real** de cada sensor
- **Exibição de valores** das últimas leituras
- **Indicadores visuais** de status (disponível, ativo, não disponível)

### Permissões e Segurança
O aplicativo solicita as seguintes permissões:
- **Sensores corporais** (BODY_SENSORS, BODY_SENSORS_BACKGROUND)
- **Localização** (ACCESS_COARSE_LOCATION, ACCESS_FINE_LOCATION)
- **Atividade** (ACTIVITY_RECOGNITION)
- **Bluetooth** (BLUETOOTH_SCAN, BLUETOOTH_CONNECT)
- **Notificações** (POST_NOTIFICATIONS)
- **Alta taxa de amostragem** (HIGH_SAMPLING_RATE_SENSORS)

## 🔧 Como Usar

1. **Instalar o aplicativo** no relógio Wear OS
2. **Conceder permissões** solicitadas
3. **Tocar em "Iniciar sensores"** para começar o monitoramento
4. **Visualizar os dados** em tempo real de cada sensor
5. **Tocar em "Parar sensores"** para encerrar o monitoramento

## 📊 Sensores Monitorados

O aplicativo detecta e monitora todos os sensores disponíveis no dispositivo, incluindo:

- **Sensores de movimento** (acelerômetro, giroscópio)
- **Sensores de ambiente** (temperatura, umidade, pressão)
- **Sensores de saúde** (frequência cardíaca, oxímetro)
- **Sensores de localização** (GPS, bússola)
- **Sensores de proximidade** e outros sensores específicos do dispositivo

## 🚀 Configuração do Projeto

### Pré-requisitos
- Android Studio Hedgehog ou superior
- JDK 11
- Dispositivo Wear OS ou emulador

### Build e Execução
```bash
# Clone o repositório
git clone [url-do-repositorio]

# Abra o projeto no Android Studio
# Sincronize o projeto com Gradle
# Execute no dispositivo/emulador Wear OS
```

### Dependências Principais
```kotlin
// Wear OS
implementation("com.google.android.gms:play-services-wearable:19.0.0")

// Compose
implementation(platform("androidx.compose:compose-bom:2024.09.00"))
implementation("androidx.wear.compose:compose-material:1.4.1")
implementation("androidx.wear.compose:compose-foundation:1.4.1")

// Lifecycle
implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.2")
```

## 📋 Estrutura de Dados

### SensorState
```kotlin
data class SensorState(
    val name: String,           // Nome do sensor
    val isAvailable: Boolean,   // Se está disponível
    val isWorking: Boolean,     // Se está funcionando
    val lastValue: String,      // Última leitura
    val status: String          // Status descritivo
)
```

## 🎨 Interface do Usuário

### Componentes Principais
- **SensorTestScreen**: Tela principal com lista de sensores
- **SensorCard**: Card individual para cada sensor
- **Botões de controle**: Iniciar/Parar monitoramento
- **Indicadores visuais**: Ícones de status coloridos

### Design System
- **Material Design** para Wear OS
- **Cores semânticas**: Verde (ativo), Amarelo (aviso), Cinza (indisponível)
- **Layout responsivo** otimizado para telas circulares

## 🔍 Logs e Debug

O aplicativo inclui logs detalhados para debug:
```kotlin
Log.d("xing", "sensor ${sensor.name} - type ${sensor.type} - vendor ${sensor.vendor}")
```

## 📱 Compatibilidade

- **Versão mínima**: Android 13 (API 33)
- **Versão alvo**: Android 15 (API 36)
- **Plataforma**: Wear OS exclusivamente
- **Modo standalone**: Funciona independentemente do smartphone

## 🤝 Contribuição

Para contribuir com o projeto:
1. Faça um fork do repositório
2. Crie uma branch para sua feature
3. Implemente suas mudanças
4. Teste em dispositivo Wear OS
5. Envie um pull request

---

**Desenvolvido por Davi Gomes Florencio**  
*Projeto para teste e monitoramento de sensores em relógios Wear OS* 