var data = [
    { name : "アシマリ", image : "asimari.gif", point : "3",id:"1"},
    { name : "ワンパチ", image : "wanpati.jpg", point : "2",id:"2"},
    { name : "イーブイ", image : "ibui.gif", point : "5",id:"3"},
    { name : "ロコン", image : "locon.gif", point : "4",id:"4"},
    { name : "ヤドン", image : "yadon.gif", point : "2",id:"5"},
    { name : "ピカチュウ", image : "pikachu02.gif", point : "5",id:"6"},
    { name : "ニャース", image : "nyasu.gif", point : "4",id:"7"},
    { name : "モクロー", image : "mokurow.gif", point : "3",id:"8"},
    { name : "コダック", image : "koduck.gif", point : "4",id:"9"},
    { name : "カビゴン", image : "kabigon.gif", point : "7",id:"10"},
    { name : "コイキング", image : "koiking.gif", point : "1",id:"11"},
    { name : "ニャビー", image : "nyabi.gif", point : "3",id:"12"},
    { name : "プリン", image : "prin.gif", point : "2",id:"13"},
    { name : "ソーナンス", image : "sonance.gif", point : "6",id:"14"},
    { name : "ゼニガメ", image : "zenigame.gif", point : "3",id:"15"},
    { name : "ヒトカゲ", image : "hitokage.gif", point : "3",id:"16"},
    { name : "メタモン", image : "metamon.gif", point : "5",id:"17"},
    { name : "ラッキー", image : "lucky.gif", point : "6",id:"18"},
    { name : "ラプラス", image : "raprace.gif", point : "7",id:"19"},
    { name : "ギャラドス", image : "gyarados.gif", point : "8",id:"20"},
    { name : "ゲンガー", image : "genga.gif", point : "8",id:"21"},
    { name : "サンダー", image : "thunder.gif", point : "9",id:"22"},
    { name : "サンダース", image : "thundersu.gif", point : "7",id:"23"},
    { name : "シャワーズ", image : "shawerz.gif", point : "7",id:"24"},
    { name : "ピチュー", image : "pityu.gif", point : "2",id:"25"},
    { name : "ファイヤー", image : "fire.gif", point : "9",id:"26"},
    { name : "フリーザ―", image : "freezer.gif", point : "9",id:"27"},
    { name : "ミュウ", image : "miu.gif", point : "9",id:"28"},
    { name : "ミュウツー", image : "miutwo.gif", point : "10",id:"29"},
    { name : "ライチュウ", image : "rityu.gif", point : "7",id:"30"},
    { name : "ブースター", image : "booster.gif", point : "7",id:"31"},
    { name : "リザードン", image : "rizardon.gif", point : "8",id:"32"},
    { name : "フシギダネ", image : "fusigidane.gif", point : "3",id:"33"},
    { name : "カイリュー", image : "kairyu.gif", point : "8",id:"34"}
    ];

var datagoods = [
    { name : "キズ薬", image : "drug.png", id : "1"},
    {name : "モンスターボール", image : "monsterball.gif", id : "2"}
    ];

var mymap = L.map('map',{
    dragging: false, // マウスドラッグによるパン操作を不可
    touchZoom: false, // タッチによるズーム操作を不可
    scrollWheelZoom: false, // スクロールによるズーム操作を不可
    doubleClickZoom: false, // ダブルクリックによるズーム操作を不可
    boxZoom: false, // [Shift] + ドラッグによるボックスズーム操作を不可
    tap: false, // タップによるズーム操作を不可
    keyboard: false, // キーボードによる操作を不可
    zoomControl: false // ズーム コントロールの非表示
});

L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
  maxZoom: 19,//ズーム
  attribution: 'Map data &copy; <a href="http://openstreetmap.org">OpenStreetMap</a> contributors, '
 }).addTo(mymap);

var Departure_lat;
var Departure_lng;
var totalpoint = 0;
var drugStock = 0;
var idStock=0;

mymap.on('locationfound', onLocationFound);
mymap.on('locationerror', onLocationError);

mymap.locate({setView: true, maxZoom: 19, timeout: 20000});

function onLocationFound(e) {
    Departure_lat = e.latlng.lat;
    Departure_lng = e.latlng.lng;
//Departure_lat=35.706036
//Departure_lng=139.708380

    for(var k=0;k<data.length;k++){
        creatPokemon();
    }
    for(var k=0;k<parseInt((data.length)/3);k++){
        creatGood();
    }
    setInterval("move()", 50);
}

function onLocationError(e) {
    alert("Error." + e.message);
}

function creatPokemon(){
    /*ポケモンの表示*/
    var j = parseInt(Math.random() * data.length);
    idStock=j;
    if(j==28||j==29){
        var r=parseInt(Math.random()*10);
        if(r==7){makeMarker();}else{creatPokemon();}
    }else if(j==22||j==26||j==27){
        var r=parseInt(Math.random()*5);
        if(r==3){makeMarker();}else{creatPokemon();}
    }else if(j==19||j==20||j==21||j==23||j==24||j==30||j==31||j==32||j==34){
        var r=parseInt(Math.random()*3);
        if(r==2){makeMarker();}else{creatPokemon();}
    }else{
        makeMarker();
    }
};

function creatGood(){
    var r = parseInt(Math.random() * datagoods.length);
    var good_lat = Math.random()*(35.708326-35.704787) + Departure_lat - (35.708326-35.704787)/2;
    var good_lng = Math.random()*(139.708750 -139.703451) + Departure_lng - (139.708750 -139.703451)/2;
    var goodIcon = L.icon({
        iconUrl: datagoods[r].image,
        iconRetinaUrl: datagoods[r].image,
        iconSize:[50, 50],
        iconAnchor:[25,50],
        popupAnchor:[0, -50],
    });
    var mapMarker = L.marker([good_lat, good_lng], { icon: goodIcon}).addTo(mymap);
    var comment = datagoods[r].name;
    mapMarker.on('click',function(e){
        if(Math.abs(good_lat-Departure_lat)/0.000008983148<18&&Math.abs(good_lng-Departure_lng)/0.000010966382<18){
            alert(comment+" を手に入れた！");
            mymap.removeLayer(mapMarker);
            var goodId = parseInt(datagoods[r].id)
            Android.addGoods(goodId);
            }else{alert("ちょっと遠すぎる！");}
    });
};

function makeMarker(){
    var poke_lat = Math.random()*(35.708326-35.704787) + Departure_lat - (35.708326-35.704787)/2;
    var poke_lng = Math.random()*(139.708750 -139.703451) + Departure_lng - (139.708750 -139.703451)/2;
    var sampleIcon = L.icon({
        iconUrl: data[idStock].image,
        iconRetinaUrl: data[idStock].image,
        iconSize:[50, 50],
        iconAnchor:[25,50],
        popupAnchor:[0, -50],
    });
    var mapMarker = L.marker([poke_lat, poke_lng], { icon: sampleIcon}).addTo(mymap);
    var comment = data[idStock].name;
    var gamepoint = parseInt(data[idStock].point);
    var id = parseInt(data[idStock].id);
    mapMarker.on('click',function(e){
    if(Math.abs(poke_lat-Departure_lat)/0.000008983148<18&&Math.abs(poke_lng-Departure_lng)/0.000010966382<18){
        alert("あ！野生の"+comment+"が現れた！");
        var ds=parseInt(drugStock);
        Android.changeActivity(gamepoint,id);
     }else{alert("ちょっと遠すぎる！");}
     });
};

function move() {
    var theta = Android.stepDetection();
    if(theta != -1000){
        Departure_lat = Departure_lat + Math.cos(theta * Math.PI/180) * 5 / 111263.284;
        Departure_lng = Departure_lng - Math.sin(theta * Math.PI/180) * 5 / (3600 * 25.3219892);
    }//何m進んでいるか
    mymap.panTo( new L.LatLng( Departure_lat, Departure_lng ) ) ;

}
