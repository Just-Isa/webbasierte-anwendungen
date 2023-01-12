<template>
    <div class="boxUmTitel">
        {{beschreibung}} ------------ {{anzahlgebote}} ------------ {{preis}} ------------ <button v-on:click="offen = !offen">V</button>
        <span v-if="offen">
            <div class="kleinereBox">Letztes gebot ------------ {{preis}} EUR (mindestpreis war {{mindestpreis}} EUR)</div>
            <div class="kleinereBox">
                Abholort ------------ <GeoLink :lat="angebot.lat" :lon="angebot.lon" > {{angebot.abholort}} </GeoLink> 
            </div>
            <div class="kleinereBox">bei ------------ {{angebot.anbietername}}</div>
            <div class="kleinereBox">bis ------------ {{new Date(angebot.ablaufzeitpunkt).toLocaleDateString()}} um {{new Date(angebot.ablaufzeitpunkt).toLocaleTimeString().replace("AM","Uhr").replace("PM", "Uhr")}}</div>
        </span>
    </div>
</template>
<script setup lang="ts">
    import GeoLink from '@/components/GeoLink.vue'
    import type {AngebotListeDing} from '@/services/IAngebotListeItem'
    import { defineProps, reactive, ref, toRef } from 'vue';
    const props = withDefaults(
        defineProps<{angebot: AngebotListeDing}>(),
        {}
    );

    const beschreibung = props.angebot["beschreibung"];
    const anzahlgebote = toRef(props.angebot, "gebote").value;
    const preis = toRef(props.angebot, "topgebot").value;
    const mindestpreis = props.angebot["mindestpreis"];

    const offen = ref(false);

</script>

<style scoped>
.boxUmTitel {
    display: block;
    align-self: center;
    background-color: black;
    color: white;
    width: fit-content;
}

.kleinereBox {
    font-size: small;
    color: darkgray;
}
</style>