<script setup lang="ts">
import GeoLink from '@/components/GeoLink.vue'
import { useFakeAngebot } from '@/services/useFakeAngebot';
import { reactive, ref } from 'vue';
import AngebotListeItem from '../components/AngebotListeItem.vue';
import AngebotListe from '@/components/AngebotListe.vue';
import { computed } from '@vue/reactivity';

const a = useFakeAngebot().angebote;

function bieten() {
  useFakeAngebot().bietenSimulieren();
}
</script>

<template>
  <div class="GeoLinks">
    <p>
      <GeoLink :lat="52.9849" :lon="9.5794">Visselhövede</GeoLink>
      <br/>
      <GeoLink :lat="52.9849" :lon="9.5794" :zoom="15" >Visselhövede</GeoLink>
    </p>
  </div>
  <div class="angebot-anzeige">
    <p>
      <AngebotListe :angebote="a" />
    </p>
  <button v-on:click="bieten()">Bieten simulieren</button>
  </div>
</template>

<style>
@media (min-width: 1024px) {
  .about {
    min-height: 100vh;
    display: flex;
    align-items: center;
  }
}
</style>
