package ru.mediasoft.unipolls.other.router;

import android.os.Bundle;

import ru.terrakok.cicerone.commands.Command;

class ExitWithResult implements Command {
    private Bundle result;

    ExitWithResult(Bundle bundle) {
        result = bundle;
    }

    Bundle getResult() {
        return result;
    }
}