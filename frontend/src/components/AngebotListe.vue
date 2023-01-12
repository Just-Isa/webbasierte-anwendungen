<template>
    <p>
      <input type="text" v-model="suchfeld" placeholder="Suchbegriff" /> <button v-on:click="suchClear()">Clear</button>
    </p>
    <table>
        <tr v-for="i in listitems" :key="i.angebotid"> 
            <AngebotListeItem :angebot="i" /> 
        </tr>
    </table>
</template>

<script setup lang="ts">
    import AngebotListeItem from '../components/AngebotListeItem.vue';
    import type {AngebotListeDing} from '@/services/IAngebotListeItem';
    import { ref, computed, type SetupContext } from 'vue';
    import { useAngebot } from '@/services/useAngebot';

    const suchfeld = ref("");
    const listitems = computed(() => {
        if (suchfeld.value.length < 2) {
            return useAngebot().angebote.angebotliste;
        } else {
            let x : AngebotListeDing[] = useAngebot().angebote.angebotliste.filter((e : any) => e["abholort"].toLowerCase().includes(suchfeld.value.toLowerCase()));
            let y : AngebotListeDing[] = useAngebot().angebote.angebotliste.filter((e : any) => e["anbietername"].toLowerCase().includes(suchfeld.value.toLowerCase()));
            let z : AngebotListeDing[] = useAngebot().angebote.angebotliste.filter((e : any) => e["beschreibung"].toLowerCase().includes(suchfeld.value.toLowerCase()));
            console.log(x.concat(y).concat(z));
            return x.concat(y).concat(z);
        }
    });

    function suchClear() {
        suchfeld.value = "";
    }
</script>