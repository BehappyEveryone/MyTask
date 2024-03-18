package com.youhajun.ui.models.sideEffects

sealed class GptSideEffect {
    data class Toast(val text: String) : GptSideEffect()
    object HideKeyboard : GptSideEffect()
    object DrawerMenuOpen : GptSideEffect()
    object DrawerMenuClose : GptSideEffect()
    sealed class Navigation: GptSideEffect() {
        object NavigateUp : Navigation()
    }
}