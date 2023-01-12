<template>
    <h1>Wir haben aktuell {{lemao.angebote.angebotliste.length}} Angebote für Sie</h1>
    <button v-on:click="updateAngebote()">Update</button>
    <div v-if="errormessage.length >= 1">
        {{errormessage}}
    </div>
    <div>
        <AngebotListe/>
    </div>
</template>

<script setup lang="ts">
import AngebotListe from '@/components/AngebotListe.vue';
import { useAngebot } from '@/services/useAngebot';
import { onMounted } from 'vue';

const lemao = useAngebot();
const errormessage = lemao.angebote.errormessage;

onMounted(() => {
    useAngebot().receiveAngebotMessages();
    useAngebot().updateAngebote();
});

function updateAngebote() {
    useAngebot().updateAngebote();;
}
</script>