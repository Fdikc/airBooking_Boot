<!--  -->
<template>
  <div class="swiper-box">
    <swiper class="mySwiper" 
    :loop="true" 
    :autoplay="{autoplay: true, delay: 2000, disableOnInteraction: false }" 
    :modules="modules" 
    effect="fade" 
    :pagination="{ clickable: true}">
      <swiper-slide  v-for="(img,index) in imgs" :key="index"><img :src="img" alt=""></swiper-slide>
  </swiper>
  </div>
</template>
<script setup>
import { ref, reactive } from 'vue'
import {Pagination, EffectFade, Autoplay  } from 'swiper';
import { Swiper, SwiperSlide } from "swiper/vue";
import 'swiper/css';
import 'swiper/css/effect-fade';
import 'swiper/css/pagination'
const modules = [Pagination, EffectFade, Autoplay ]
const imgs = [
  require('@/assets/img/banner1.jpg'),
  require('@/assets/img/banner2.jpg'),
  require('@/assets/img/banner3.jpg'),
  require('@/assets/img/banner4.jpg'),
]
</script>
<style lang='scss' scoped>
.swiper-box{
  width: 100%;
  height: 550px;
  position: relative;
}
.swiper {
  width: 100%;
  height: 100%;
}

.swiper-slide {
  text-align: center;
  font-size: 18px;
  background: #fff;

  /* Center slide text vertically */
  display: -webkit-box;
  display: -ms-flexbox;
  display: -webkit-flex;
  display: flex;
  -webkit-box-pack: center;
  -ms-flex-pack: center;
  -webkit-justify-content: center;
  justify-content: center;
  -webkit-box-align: center;
  -ms-flex-align: center;
  -webkit-align-items: center;
  align-items: center;
}

.swiper-slide img {
  display: block;
  width: 100%;
  height: 100%;
  object-fit: cover;
}
.swiper-horizontal>.swiper-pagination-bullets, .swiper-pagination-bullets.swiper-pagination-horizontal, .swiper-pagination-custom, .swiper-pagination-fraction {
    bottom: var(--swiper-pagination-bottom,300px);
    top: var(--swiper-pagination-top,auto);
    left: 0;
    width: 100%;
}
</style>