<template>
    <div>
        <Header></Header>
        <router-view></router-view>
        <Footer></Footer>
    </div>
</template>

<script>
    import Header from './components/Header'
    import Footer from './components/Footer'

    export default {
        name: 'app',
        components: {
            Header,
            Footer,
        }
    }
</script>

<style>
    body{
        /*background: #f8f8f8;*/
    }
</style>
