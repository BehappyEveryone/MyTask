package com.youhajun.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.youhajun.domain.models.UiStateErrorVo
import com.youhajun.domain.models.enums.SignalingType
import com.youhajun.domain.models.inspectUiState
import com.youhajun.domain.models.sealeds.WebSocketState
import com.youhajun.domain.usecase.room.ConnectLiveRoomUseCase
import com.youhajun.domain.usecase.room.DisposeLiveRoomUseCase
import com.youhajun.domain.usecase.room.GetRoomSignalingCommandUseCase
import com.youhajun.domain.usecase.room.GetRoomSocketStateUseCase
import com.youhajun.domain.usecase.room.GetRoomWebRTCSessionUseCase
import com.youhajun.domain.usecase.room.SendLiveRoomSignalingMsgUseCase
import com.youhajun.ui.models.sideEffects.LiveRoomSideEffect
import com.youhajun.ui.models.states.LiveRoomState
import com.youhajun.ui.utils.webRtc.WebRTCContract
import com.youhajun.ui.utils.webRtc.managers.WebRtcManager
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject
import kotlin.properties.Delegates

interface LiveRoomIntent {
    fun onClickHeaderBackIcon()
}

@HiltViewModel(assistedFactory = LiveRoomViewModel.LiveRoomViewModelFactory::class)
class LiveRoomViewModel @Inject constructor(
    @Assisted private val roomIdx: Long,
    private val connectLiveRoomUseCase: ConnectLiveRoomUseCase,
    private val disposeLiveRoomUseCase: DisposeLiveRoomUseCase,
    private val sendLiveRoomSignalingMsgUseCase: SendLiveRoomSignalingMsgUseCase,
    private val getRoomSocketStateUseCase: GetRoomSocketStateUseCase,
    private val getRoomSignalingCommandUseCase: GetRoomSignalingCommandUseCase,
    private val getRoomWebRTCSessionUseCase: GetRoomWebRTCSessionUseCase,
    webRtcSessionManagerFactory: WebRTCContract.Factory
) : ContainerHost<LiveRoomState, LiveRoomSideEffect>, ViewModel(), LiveRoomIntent, WebRTCContract.Signaling {

    @AssistedFactory
    interface LiveRoomViewModelFactory {
        fun create(roomIdx: Long): LiveRoomViewModel
    }

    override val container: Container<LiveRoomState, LiveRoomSideEffect> = container(LiveRoomState()) {
        onGetRoomSocketState()

        onGetRoomSignalingCommand()

        onGetRoomWebRTCSession()

        onConnectLiveRoom()
    }

    private val _signalingCommandFlow = MutableSharedFlow<Pair<SignalingType, String>>()
    override val signalingCommandFlow: Flow<Pair<SignalingType, String>> = _signalingCommandFlow

    private val sessionManager: WebRTCContract.SessionManager = webRtcSessionManagerFactory.createSessionManager(this)
    override fun onClickHeaderBackIcon() {
        intent { postSideEffect(LiveRoomSideEffect.Navigation.NavigateUp) }
    }

    override fun sendCommand(signalingCommand: SignalingType, message: String) {
        viewModelScope.launch {
            sendLiveRoomSignalingMsgUseCase(signalingCommand to message)
        }
    }
    override fun dispose() {
        viewModelScope.launch {
            disposeLiveRoomUseCase(Unit)
        }
    }

    override fun onCleared() {
        super.onCleared()

        sessionManager.disconnect()
    }

    private fun onConnectLiveRoom() {
        viewModelScope.launch {
            connectLiveRoomUseCase(Unit)
        }
    }

    private fun onGetRoomSocketState() {
        viewModelScope.launch {
            getRoomSocketStateUseCase(Unit).collect {
                it.inspectUiState(
                    onError = ::handleWebSocketStateError,
                    onSuccess = ::handleWebSocketStateSuccess
                )
            }
        }
    }

    private fun handleWebSocketStateSuccess(wss: WebSocketState) {
        when(wss) {
            WebSocketState.Close -> {
                //TODO 소켓 닫기 성공
            }
            WebSocketState.Open -> {
                sessionManager.onSessionScreenReady()
            }
            else -> Unit
        }
    }

    private fun handleWebSocketStateError(errorVo: UiStateErrorVo<WebSocketState>) {
        when(errorVo.data) {
            is WebSocketState.Failure -> {
                //TODO 소켓 연결 혹은 메세지 전송 실패
                errorVo.code
                errorVo.message
            }
            is WebSocketState.Close -> {
                //TODO 소켓 닫기 실패
            }
            else -> Unit
        }
    }

    private fun onGetRoomSignalingCommand() {
        viewModelScope.launch {
            getRoomSignalingCommandUseCase(Unit).collect {
                _signalingCommandFlow.emit(it)
            }
        }
    }

    private fun onGetRoomWebRTCSession() {
        viewModelScope.launch {
            getRoomWebRTCSessionUseCase(Unit).collect {
                intent {
                    reduce { state.copy(webRTCSessionType = it) }
                }
            }
        }
    }
}