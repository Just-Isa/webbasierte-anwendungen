<template>
    <a target="_blank" :href="oSLink"><slot>Dummy</slot></a>
</template>

<script setup lang="ts">
    import { defineProps} from 'vue';
    const props = withDefaults(
        defineProps<{lat: number, lon: number, zoom?: number}>(),
            {zoom: 17}
    )
    const oSLink = `https://www.openstreetmap.org/#map=${props.zoom}/${props.lat}/${props.lon}`;
</script>