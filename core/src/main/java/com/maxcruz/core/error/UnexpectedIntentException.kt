package com.maxcruz.core.error

import com.maxcruz.core.presentation.mvi.MVIIntent

class UnexpectedIntentException(intent: MVIIntent) : IllegalArgumentException(
    "The provided intent was not expected in the processing chain: $intent"
)