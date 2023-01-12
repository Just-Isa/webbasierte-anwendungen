import type { IAngebotListeItem } from "@/services/IAngebotListeItem";
import type { IBackendInfoMessage } from "./IBackendInfoMessage";
import { reactive, readonly } from "vue";

import { Client } from '@stomp/stompjs';
import { stringifyQuery } from "vue-router";
const wsurl = `ws://${window.location.host}/stompbroker`

interface ILoginState {
    username: string,
    name: string,
    benutzerprofilid: number,
    loggedin: boolean,
    jwtToken: string
    errormessage: string
}

const loginState : ILoginState =    /* reaktives Objekt zu Interface ILoginState */

// Analog Java-Records auf Serverseite:
// public record JwtLoginResponseDTO(String username, String name, Long benutzerprofilid, String jwtToken) {};
interface IJwtLoginResponseDTO {
    username: string,
    name: string,
    benutzerprofilid: number,
    jwtToken: string
}

// public record JwtLoginRequestDTO(String username, String password) {};
interface IJwtLoginRequestDTO {
    username: string,
    password: string
}


async function login(username: string, password: string) {
   /*
    * sendet per fetch() POST auf Endpunkt /api/login ein IAddGebotRequestDTO als JSON
    * erwartet IJwtLoginResponseDTD-Struktur zurück (JSON)
    * 
    * Falls ok, wird 'errormessage' im State auf leer gesetzt,
    * die loginState-Eigenschaften aus der Antwort befüllt
    * und 'loggedin' auf true gesetzt
    * 
    * Falls Fehler, wird ein logout() ausgeführt und auf die Fehlermeldung in 'errormessage' geschrieben
    */


}

function logout() {
    console.log(`logout(${loginState.name} [${loginState.username}])`)
    loginState.loggedin = false
    loginState.jwtToken = ""
    loginState.benutzerprofilid = 0
    loginState.name = ""
    loginState.username = ""
}


export function useLogin() {
    return {
        logindata: readonly(loginState),
        login,
        logout,
    }
}

