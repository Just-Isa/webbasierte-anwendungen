<template>
    <h1>Versteigerung {{angebot.beschreibung}}</h1>    
    <div v-if="errormessage.length >= 1">
        {{errormessage}}
    </div>
    <div>von {{angebot.anbietername}}, abholbar in <GeoLink :lat="angebot.lat" :lon="angebot.lon"> bis {{new Date(angebot.ablaufzeitpunkt.toLocaleDateString())}} </div>
    </br>
    <div>
        Bisheriges Topgebot von EUR {{gebotState.topgebot}} ist von {{gebotState.topbieter}}
        <p>
            <input type="number" v-model="neuGebot" placeholder="0" /> <button v-on:click="bieten()">Clear</button>
        </p>
        <table>
            <tr v-for="i in gebotliste" :key="i.gebotid"> 
                <div>{{i.beschreibung}}<div>
            </tr>
        </table>
    </div>
</template>

<script setup lang="ts">
    import AngebotListe from '@/components/AngebotListe.vue';
    import GeoLink from '@/components/GeoLink.vue'
    import { useAngebot } from '@/services/useAngebot';
    import { useGebot } from '@/services/useGebot';


    const props = defineProps<{angebotidstr: String}>();
    const angebot: IAngebotListeItem = null;
    const neuGebot = ref(0);

    useAngebot().angebote.angebotsliste.forEach(element => {
        if (element.angebotid == Number(props.angebotidstr)) {
            angebot = element;
            break;
        }
    });

    const gebotState = reactive(useGebot().gebotState);

    const gebotListe = computed(() => {
        let lmaoOne = useGebot().gebotState.gebotliste.sort((objA, objB) => objA.ablaufzeitpunkt.getTime() - objB.date.getTime());
        return lmaoOne.slice(0, 10);
    });

    function bieten() {
        useGebot().sendeGebot(neuGebot);
    }
    
    onMounted(() => {
        useGebot().updateGebote();
    });
</script>