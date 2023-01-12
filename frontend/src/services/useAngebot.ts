import { Client, type Message} from "@stomp/stompjs";
import { reactive, readonly } from "vue";
import type { AngebotListeDing, IAngebotListeItem } from "./IAngebotListeItem";

export interface IAngebotState {
    angebotliste: IAngebotListeItem[],
    errormessage: string
}

const angebotState : IAngebotState = reactive({
    angebotliste: [],
    errormessage: ""
});

export function useAngebot() {
    return {
        angebote: readonly(angebotState),
        updateAngebote,
        receiveAngebotMessages
    }
}

export function receiveAngebotMessages() {
    const wsurl = `ws://${window.location.host}/stompbroker`;
    const DEST = "/topic/angebot";

    const stompclient = new Client({brokerURL: wsurl});
    stompclient.onWebSocketError = (event) => { console.log("websocketError")};
    stompclient.onStompError = (event) => { console.log("FEHLER stompError")};

    stompclient.onConnect = (frame) => {
        stompclient.subscribe(DEST, (message) => {
            message = JSON.parse(message.body);
            updateAngebote();
        });
    };
    stompclient.onDisconnect = () => { console.log("Verbindung zu Stomp weg")};
    stompclient.activate();
}

export function updateAngebote() : void {
    fetch('/api/angebot', {
        method: 'GET'
    })
    .then((response) => {
        if (!response.ok) {
            angebotState.errormessage = "Somethings wrong bud";
        }
        return response.json();
    })
    .then((jsondata) => {
        angebotState.angebotliste = jsondata;
        angebotState.errormessage = "";
    })
    .catch((fehler) => {
        angebotState.errormessage = fehler;
    });
}