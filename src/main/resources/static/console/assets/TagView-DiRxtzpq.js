import{aK as he,aL as ne,aM as Ne,aN as ae,aO as oe,aP as le,aQ as be,aR as pe,aS as ut,aT as He,aU as te,aV as qe,aW as je,d as B,a7 as i,f as b,af as j,aX as ke,aY as xe,aZ as Tt,x as Ke,ac as ct,a_ as dt,a3 as Rt,a$ as Se,b0 as Ge,b1 as Ze,b2 as Xe,b3 as Pt,b4 as ve,b5 as U,b6 as Vt,b7 as q,b8 as et,b9 as Nt,ba as zt,bb as Mt,bc as We,bd as Bt,be as Et,bf as Ot,bg as Ft,bh as Lt,bi as Ht,bj as ht,bk as qt,ae as jt,ah as Ee,bl as tt,E as Kt,bm as rt,bn as Gt,bo as Zt,bp as Xt,bq as Te,B as re,aj as Wt,c as G,C as pt,o as M,D as gt,n as ft,w as f,a as p,u as h,F as Yt,p as _e,G as Ye,S as Qt,T as vt,m as ze,M as Jt,b as er,J as mt,X as tr,Y as rr,r as bt,R as kt,br as ue,g as ar,h as or,Z as nr,$ as Re,y as lr,a0 as ir,a1 as sr,t as ur,A as Oe,P as at,Q as ot,U as nt,a5 as cr,a6 as dr,aH as hr,V as pr,aw as gr,ax as Fe,bs as fr,H as vr,a8 as lt,s as Le}from"./index-C97z5PQV.js";import{_ as xt,b as mr,t as it,d as br,a as kr,u as xr}from"./tagApi-CoS0Bswb.js";import{N as wr}from"./ButtonGroup-crtXYHAe.js";import{N as yr}from"./Badge-Dj7OGGqR.js";function Cr(e,t){switch(e[0]){case"hex":return t?"#000000FF":"#000000";case"rgb":return t?"rgba(0, 0, 0, 1)":"rgb(0, 0, 0)";case"hsl":return t?"hsla(0, 0%, 0%, 1)":"hsl(0, 0%, 0%)";case"hsv":return t?"hsva(0, 0%, 0%, 1)":"hsv(0, 0%, 0%)"}return"#000000"}function Ue(e){return e===null?null:/^ *#/.test(e)?"hex":e.includes("rgb")?"rgb":e.includes("hsl")?"hsl":e.includes("hsv")?"hsv":null}function Sr(e){return e=Math.round(e),e>=360?359:e<0?0:e}function _r(e){return e=Math.round(e*100)/100,e>1?1:e<0?0:e}const Ur={rgb:{hex(e){return le(te(e))},hsl(e){const[t,o,r,l]=te(e);return ne([...je(t,o,r),l])},hsv(e){const[t,o,r,l]=te(e);return pe([...qe(t,o,r),l])}},hex:{rgb(e){return ae(te(e))},hsl(e){const[t,o,r,l]=te(e);return ne([...je(t,o,r),l])},hsv(e){const[t,o,r,l]=te(e);return pe([...qe(t,o,r),l])}},hsl:{hex(e){const[t,o,r,l]=be(e);return le([...He(t,o,r),l])},rgb(e){const[t,o,r,l]=be(e);return ae([...He(t,o,r),l])},hsv(e){const[t,o,r,l]=be(e);return pe([...ut(t,o,r),l])}},hsv:{hex(e){const[t,o,r,l]=he(e);return le([...oe(t,o,r),l])},rgb(e){const[t,o,r,l]=he(e);return ae([...oe(t,o,r),l])},hsl(e){const[t,o,r,l]=he(e);return ne([...Ne(t,o,r),l])}}};function wt(e,t,o){return o=o||Ue(e),o?o===t?e:Ur[o][t](e):null}const Ce="12px",Ir=12,ce="6px",Ar=B({name:"AlphaSlider",props:{clsPrefix:{type:String,required:!0},rgba:{type:Array,default:null},alpha:{type:Number,default:0},onUpdateAlpha:{type:Function,required:!0},onComplete:Function},setup(e){const t=b(null);function o(n){!t.value||!e.rgba||(ke("mousemove",document,r),ke("mouseup",document,l),r(n))}function r(n){const{value:u}=t;if(!u)return;const{width:d,left:m}=u.getBoundingClientRect(),g=(n.clientX-m)/(d-Ir);e.onUpdateAlpha(_r(g))}function l(){var n;xe("mousemove",document,r),xe("mouseup",document,l),(n=e.onComplete)===null||n===void 0||n.call(e)}return{railRef:t,railBackgroundImage:j(()=>{const{rgba:n}=e;return n?`linear-gradient(to right, rgba(${n[0]}, ${n[1]}, ${n[2]}, 0) 0%, rgba(${n[0]}, ${n[1]}, ${n[2]}, 1) 100%)`:""}),handleMouseDown:o}},render(){const{clsPrefix:e}=this;return i("div",{class:`${e}-color-picker-slider`,ref:"railRef",style:{height:Ce,borderRadius:ce},onMousedown:this.handleMouseDown},i("div",{style:{borderRadius:ce,position:"absolute",left:0,right:0,top:0,bottom:0,overflow:"hidden"}},i("div",{class:`${e}-color-picker-checkboard`}),i("div",{class:`${e}-color-picker-slider__image`,style:{backgroundImage:this.railBackgroundImage}})),this.rgba&&i("div",{style:{position:"absolute",left:ce,right:ce,top:0,bottom:0}},i("div",{class:`${e}-color-picker-handle`,style:{left:`calc(${this.alpha*100}% - ${ce})`,borderRadius:ce,width:Ce,height:Ce}},i("div",{class:`${e}-color-picker-handle__fill`,style:{backgroundColor:ae(this.rgba),borderRadius:ce,width:Ce,height:Ce}}))))}}),Qe=Tt("n-color-picker");function $r(e){return/^\d{1,3}\.?\d*$/.test(e.trim())?Math.max(0,Math.min(Number.parseInt(e),255)):!1}function Dr(e){return/^\d{1,3}\.?\d*$/.test(e.trim())?Math.max(0,Math.min(Number.parseInt(e),360)):!1}function Tr(e){return/^\d{1,3}\.?\d*$/.test(e.trim())?Math.max(0,Math.min(Number.parseInt(e),100)):!1}function Rr(e){const t=e.trim();return/^#[0-9a-fA-F]+$/.test(t)?[4,5,7,9].includes(t.length):!1}function Pr(e){return/^\d{1,3}\.?\d*%$/.test(e.trim())?Math.max(0,Math.min(Number.parseInt(e)/100,100)):!1}const Vr={paddingSmall:"0 4px"},st=B({name:"ColorInputUnit",props:{label:{type:String,required:!0},value:{type:[Number,String],default:null},showAlpha:Boolean,onUpdateValue:{type:Function,required:!0}},setup(e){const t=b(""),{themeRef:o}=ct(Qe,null);dt(()=>{t.value=r()});function r(){const{value:u}=e;if(u===null)return"";const{label:d}=e;return d==="HEX"?u:d==="A"?`${Math.floor(u*100)}%`:String(Math.floor(u))}function l(u){t.value=u}function n(u){let d,m;switch(e.label){case"HEX":m=Rr(u),m&&e.onUpdateValue(u),t.value=r();break;case"H":d=Dr(u),d===!1?t.value=r():e.onUpdateValue(d);break;case"S":case"L":case"V":d=Tr(u),d===!1?t.value=r():e.onUpdateValue(d);break;case"A":d=Pr(u),d===!1?t.value=r():e.onUpdateValue(d);break;case"R":case"G":case"B":d=$r(u),d===!1?t.value=r():e.onUpdateValue(d);break}}return{mergedTheme:o,inputValue:t,handleInputChange:n,handleInputUpdateValue:l}},render(){const{mergedTheme:e}=this;return i(Ke,{size:"small",placeholder:this.label,theme:e.peers.Input,themeOverrides:e.peerOverrides.Input,builtinThemeOverrides:Vr,value:this.inputValue,onUpdateValue:this.handleInputUpdateValue,onChange:this.handleInputChange,style:this.label==="A"?"flex-grow: 1.25;":""})}}),Nr=B({name:"ColorInput",props:{clsPrefix:{type:String,required:!0},mode:{type:String,required:!0},modes:{type:Array,required:!0},showAlpha:{type:Boolean,required:!0},value:{type:String,default:null},valueArr:{type:Array,default:null},onUpdateValue:{type:Function,required:!0},onUpdateMode:{type:Function,required:!0}},setup(e){return{handleUnitUpdateValue(t,o){const{showAlpha:r}=e;if(e.mode==="hex"){e.onUpdateValue((r?le:Se)(o));return}let l;switch(e.valueArr===null?l=[0,0,0,0]:l=Array.from(e.valueArr),e.mode){case"hsv":l[t]=o,e.onUpdateValue((r?pe:Xe)(l));break;case"rgb":l[t]=o,e.onUpdateValue((r?ae:Ze)(l));break;case"hsl":l[t]=o,e.onUpdateValue((r?ne:Ge)(l));break}}}},render(){const{clsPrefix:e,modes:t}=this;return i("div",{class:`${e}-color-picker-input`},i("div",{class:`${e}-color-picker-input__mode`,onClick:this.onUpdateMode,style:{cursor:t.length===1?"":"pointer"}},this.mode.toUpperCase()+(this.showAlpha?"A":"")),i(Rt,null,{default:()=>{const{mode:o,valueArr:r,showAlpha:l}=this;if(o==="hex"){let n=null;try{n=r===null?null:(l?le:Se)(r)}catch{}return i(st,{label:"HEX",showAlpha:l,value:n,onUpdateValue:u=>{this.handleUnitUpdateValue(0,u)}})}return(o+(l?"a":"")).split("").map((n,u)=>i(st,{label:n.toUpperCase(),value:r===null?null:r[u],onUpdateValue:d=>{this.handleUnitUpdateValue(u,d)}}))}}))}});function zr(e,t){if(t==="hsv"){const[o,r,l,n]=he(e);return ae([...oe(o,r,l),n])}return e}function Mr(e){const t=document.createElement("canvas").getContext("2d");return t?(t.fillStyle=e,t.fillStyle):"#000000"}const Br=B({name:"ColorPickerSwatches",props:{clsPrefix:{type:String,required:!0},mode:{type:String,required:!0},swatches:{type:Array,required:!0},onUpdateColor:{type:Function,required:!0}},setup(e){const t=j(()=>e.swatches.map(n=>{const u=Ue(n);return{value:n,mode:u,legalValue:zr(n,u)}}));function o(n){const{mode:u}=e;let{value:d,mode:m}=n;return m||(m="hex",/^[a-zA-Z]+$/.test(d)?d=Mr(d):(Pt("color-picker",`color ${d} in swatches is invalid.`),d="#000000")),m===u?d:wt(d,u,m)}function r(n){e.onUpdateColor(o(n))}function l(n,u){n.key==="Enter"&&r(u)}return{parsedSwatchesRef:t,handleSwatchSelect:r,handleSwatchKeyDown:l}},render(){const{clsPrefix:e}=this;return i("div",{class:`${e}-color-picker-swatches`},this.parsedSwatchesRef.map(t=>i("div",{class:`${e}-color-picker-swatch`,tabindex:0,onClick:()=>{this.handleSwatchSelect(t)},onKeydown:o=>{this.handleSwatchKeyDown(o,t)}},i("div",{class:`${e}-color-picker-swatch__fill`,style:{background:t.legalValue}}))))}}),Er=B({name:"ColorPickerTrigger",slots:Object,props:{clsPrefix:{type:String,required:!0},value:{type:String,default:null},hsla:{type:Array,default:null},disabled:Boolean,onClick:Function},setup(e){const{colorPickerSlots:t,renderLabelRef:o}=ct(Qe,null);return()=>{const{hsla:r,value:l,clsPrefix:n,onClick:u,disabled:d}=e,m=t.label||o.value;return i("div",{class:[`${n}-color-picker-trigger`,d&&`${n}-color-picker-trigger--disabled`],onClick:d?void 0:u},i("div",{class:`${n}-color-picker-trigger__fill`},i("div",{class:`${n}-color-picker-checkboard`}),i("div",{style:{position:"absolute",left:0,right:0,top:0,bottom:0,backgroundColor:r?ne(r):""}}),l&&r?i("div",{class:`${n}-color-picker-trigger__value`,style:{color:r[2]>50||r[3]<.5?"black":"white"}},m?m(l):l):null))}}}),Or=B({name:"ColorPreview",props:{clsPrefix:{type:String,required:!0},mode:{type:String,required:!0},color:{type:String,default:null,validator:e=>{const t=Ue(e);return!!(!e||t&&t!=="hsv")}},onUpdateColor:{type:Function,required:!0}},setup(e){function t(o){var r;const l=o.target.value;(r=e.onUpdateColor)===null||r===void 0||r.call(e,wt(l.toUpperCase(),e.mode,"hex")),o.stopPropagation()}return{handleChange:t}},render(){const{clsPrefix:e}=this;return i("div",{class:`${e}-color-picker-preview__preview`},i("span",{class:`${e}-color-picker-preview__fill`,style:{background:this.color||"#000000"}}),i("input",{class:`${e}-color-picker-preview__input`,type:"color",value:this.color,onChange:this.handleChange}))}}),me="12px",Fr=12,de="6px",Lr=6,Hr="linear-gradient(90deg,red,#ff0 16.66%,#0f0 33.33%,#0ff 50%,#00f 66.66%,#f0f 83.33%,red)",qr=B({name:"HueSlider",props:{clsPrefix:{type:String,required:!0},hue:{type:Number,required:!0},onUpdateHue:{type:Function,required:!0},onComplete:Function},setup(e){const t=b(null);function o(n){t.value&&(ke("mousemove",document,r),ke("mouseup",document,l),r(n))}function r(n){const{value:u}=t;if(!u)return;const{width:d,left:m}=u.getBoundingClientRect(),g=Sr((n.clientX-m-Lr)/(d-Fr)*360);e.onUpdateHue(g)}function l(){var n;xe("mousemove",document,r),xe("mouseup",document,l),(n=e.onComplete)===null||n===void 0||n.call(e)}return{railRef:t,handleMouseDown:o}},render(){const{clsPrefix:e}=this;return i("div",{class:`${e}-color-picker-slider`,style:{height:me,borderRadius:de}},i("div",{ref:"railRef",style:{boxShadow:"inset 0 0 2px 0 rgba(0, 0, 0, .24)",boxSizing:"border-box",backgroundImage:Hr,height:me,borderRadius:de,position:"relative"},onMousedown:this.handleMouseDown},i("div",{style:{position:"absolute",left:de,right:de,top:0,bottom:0}},i("div",{class:`${e}-color-picker-handle`,style:{left:`calc((${this.hue}%) / 359 * 100 - ${de})`,borderRadius:de,width:me,height:me}},i("div",{class:`${e}-color-picker-handle__fill`,style:{backgroundColor:`hsl(${this.hue}, 100%, 50%)`,borderRadius:de,width:me,height:me}})))))}}),Pe="12px",Ve="6px",jr=B({name:"Pallete",props:{clsPrefix:{type:String,required:!0},rgba:{type:Array,default:null},displayedHue:{type:Number,required:!0},displayedSv:{type:Array,required:!0},onUpdateSV:{type:Function,required:!0},onComplete:Function},setup(e){const t=b(null);function o(n){t.value&&(ke("mousemove",document,r),ke("mouseup",document,l),r(n))}function r(n){const{value:u}=t;if(!u)return;const{width:d,height:m,left:g,bottom:E}=u.getBoundingClientRect(),I=(E-n.clientY)/m,W=(n.clientX-g)/d,Z=100*(W>1?1:W<0?0:W),O=100*(I>1?1:I<0?0:I);e.onUpdateSV(Z,O)}function l(){var n;xe("mousemove",document,r),xe("mouseup",document,l),(n=e.onComplete)===null||n===void 0||n.call(e)}return{palleteRef:t,handleColor:j(()=>{const{rgba:n}=e;return n?`rgb(${n[0]}, ${n[1]}, ${n[2]})`:""}),handleMouseDown:o}},render(){const{clsPrefix:e}=this;return i("div",{class:`${e}-color-picker-pallete`,onMousedown:this.handleMouseDown,ref:"palleteRef"},i("div",{class:`${e}-color-picker-pallete__layer`,style:{backgroundImage:`linear-gradient(90deg, white, hsl(${this.displayedHue}, 100%, 50%))`}}),i("div",{class:`${e}-color-picker-pallete__layer ${e}-color-picker-pallete__layer--shadowed`,style:{backgroundImage:"linear-gradient(180deg, rgba(0, 0, 0, 0%), rgba(0, 0, 0, 100%))"}}),this.rgba&&i("div",{class:`${e}-color-picker-handle`,style:{width:Pe,height:Pe,borderRadius:Ve,left:`calc(${this.displayedSv[0]}% - ${Ve})`,bottom:`calc(${this.displayedSv[1]}% - ${Ve})`}},i("div",{class:`${e}-color-picker-handle__fill`,style:{backgroundColor:this.handleColor,borderRadius:Ve,width:Pe,height:Pe}})))}}),Kr=ve([U("color-picker",`
 display: inline-block;
 box-sizing: border-box;
 height: var(--n-height);
 font-size: var(--n-font-size);
 width: 100%;
 position: relative;
 `),U("color-picker-panel",`
 margin: 4px 0;
 width: 240px;
 font-size: var(--n-panel-font-size);
 color: var(--n-text-color);
 background-color: var(--n-color);
 transition:
 box-shadow .3s var(--n-bezier),
 color .3s var(--n-bezier),
 background-color .3s var(--n-bezier);
 border-radius: var(--n-border-radius);
 box-shadow: var(--n-box-shadow);
 `,[Vt(),U("input",`
 text-align: center;
 `)]),U("color-picker-checkboard",`
 background: white; 
 position: absolute;
 left: 0;
 right: 0;
 top: 0;
 bottom: 0;
 `,[ve("&::after",`
 background-image: linear-gradient(45deg, #DDD 25%, #0000 25%), linear-gradient(-45deg, #DDD 25%, #0000 25%), linear-gradient(45deg, #0000 75%, #DDD 75%), linear-gradient(-45deg, #0000 75%, #DDD 75%);
 background-size: 12px 12px;
 background-position: 0 0, 0 6px, 6px -6px, -6px 0px;
 background-repeat: repeat;
 content: "";
 position: absolute;
 left: 0;
 right: 0;
 top: 0;
 bottom: 0;
 `)]),U("color-picker-slider",`
 margin-bottom: 8px;
 position: relative;
 box-sizing: border-box;
 `,[q("image",`
 position: absolute;
 left: 0;
 right: 0;
 top: 0;
 bottom: 0;
 `),ve("&::after",`
 content: "";
 position: absolute;
 border-radius: inherit;
 left: 0;
 right: 0;
 top: 0;
 bottom: 0;
 box-shadow: inset 0 0 2px 0 rgba(0, 0, 0, .24);
 pointer-events: none;
 `)]),U("color-picker-handle",`
 z-index: 1;
 box-shadow: 0 0 2px 0 rgba(0, 0, 0, .45);
 position: absolute;
 background-color: white;
 overflow: hidden;
 `,[q("fill",`
 box-sizing: border-box;
 border: 2px solid white;
 `)]),U("color-picker-pallete",`
 height: 180px;
 position: relative;
 margin-bottom: 8px;
 cursor: crosshair;
 `,[q("layer",`
 position: absolute;
 left: 0;
 right: 0;
 top: 0;
 bottom: 0;
 `,[et("shadowed",`
 box-shadow: inset 0 0 2px 0 rgba(0, 0, 0, .24);
 `)])]),U("color-picker-preview",`
 display: flex;
 `,[q("sliders",`
 flex: 1 0 auto;
 `),q("preview",`
 position: relative;
 height: 30px;
 width: 30px;
 margin: 0 0 8px 6px;
 border-radius: 50%;
 box-shadow: rgba(0, 0, 0, .15) 0px 0px 0px 1px inset;
 overflow: hidden;
 `),q("fill",`
 display: block;
 width: 30px;
 height: 30px;
 `),q("input",`
 position: absolute;
 top: 0;
 left: 0;
 width: 30px;
 height: 30px;
 opacity: 0;
 z-index: 1;
 `)]),U("color-picker-input",`
 display: flex;
 align-items: center;
 `,[U("input",`
 flex-grow: 1;
 flex-basis: 0;
 `),q("mode",`
 width: 72px;
 text-align: center;
 `)]),U("color-picker-control",`
 padding: 12px;
 `),U("color-picker-action",`
 display: flex;
 margin-top: -4px;
 border-top: 1px solid var(--n-divider-color);
 padding: 8px 12px;
 justify-content: flex-end;
 `,[U("button","margin-left: 8px;")]),U("color-picker-trigger",`
 border: var(--n-border);
 height: 100%;
 box-sizing: border-box;
 border-radius: var(--n-border-radius);
 transition: border-color .3s var(--n-bezier);
 cursor: pointer;
 `,[q("value",`
 white-space: nowrap;
 position: relative;
 `),q("fill",`
 border-radius: var(--n-border-radius);
 position: absolute;
 display: flex;
 align-items: center;
 justify-content: center;
 left: 4px;
 right: 4px;
 top: 4px;
 bottom: 4px;
 `),et("disabled","cursor: not-allowed"),U("color-picker-checkboard",`
 border-radius: var(--n-border-radius);
 `,[ve("&::after",`
 --n-block-size: calc((var(--n-height) - 8px) / 3);
 background-size: calc(var(--n-block-size) * 2) calc(var(--n-block-size) * 2);
 background-position: 0 0, 0 var(--n-block-size), var(--n-block-size) calc(-1 * var(--n-block-size)), calc(-1 * var(--n-block-size)) 0px; 
 `)])]),U("color-picker-swatches",`
 display: grid;
 grid-gap: 8px;
 flex-wrap: wrap;
 position: relative;
 grid-template-columns: repeat(auto-fill, 18px);
 margin-top: 10px;
 `,[U("color-picker-swatch",`
 width: 18px;
 height: 18px;
 background-image: linear-gradient(45deg, #DDD 25%, #0000 25%), linear-gradient(-45deg, #DDD 25%, #0000 25%), linear-gradient(45deg, #0000 75%, #DDD 75%), linear-gradient(-45deg, #0000 75%, #DDD 75%);
 background-size: 8px 8px;
 background-position: 0px 0, 0px 4px, 4px -4px, -4px 0px;
 background-repeat: repeat;
 `,[q("fill",`
 position: relative;
 width: 100%;
 height: 100%;
 border-radius: 3px;
 box-shadow: rgba(0, 0, 0, .15) 0px 0px 0px 1px inset;
 cursor: pointer;
 `),ve("&:focus",`
 outline: none;
 `,[q("fill",[ve("&::after",`
 position: absolute;
 top: 0;
 right: 0;
 bottom: 0;
 left: 0;
 background: inherit;
 filter: blur(2px);
 content: "";
 `)])])])])]),Gr=Object.assign(Object.assign({},ht.props),{value:String,show:{type:Boolean,default:void 0},defaultShow:Boolean,defaultValue:String,modes:{type:Array,default:()=>["rgb","hex","hsl"]},placement:{type:String,default:"bottom-start"},to:We.propTo,showAlpha:{type:Boolean,default:!0},showPreview:Boolean,swatches:Array,disabled:{type:Boolean,default:void 0},actions:{type:Array,default:null},internalActions:Array,size:String,renderLabel:Function,onComplete:Function,onConfirm:Function,onClear:Function,"onUpdate:show":[Function,Array],onUpdateShow:[Function,Array],"onUpdate:value":[Function,Array],onUpdateValue:[Function,Array]}),Zr=B({name:"ColorPicker",props:Gr,slots:Object,setup(e,{slots:t}){const o=b(null);let r=null;const l=Ft(e),{mergedSizeRef:n,mergedDisabledRef:u}=l,{localeRef:d}=Lt("global"),{mergedClsPrefixRef:m,namespaceRef:g,inlineThemeDisabled:E}=Ht(e),I=ht("ColorPicker","-color-picker",Kr,qt,e,m);jt(Qe,{themeRef:I,renderLabelRef:Ee(e,"renderLabel"),colorPickerSlots:t});const W=b(e.defaultShow),Z=tt(Ee(e,"show"),W);function O(a){const{onUpdateShow:v,"onUpdate:show":w}=e;v&&Te(v,a),w&&Te(w,a),W.value=a}const{defaultValue:k}=e,Ie=b(k===void 0?Cr(e.modes,e.showAlpha):k),$=tt(Ee(e,"value"),Ie),P=b([$.value]),F=b(0),we=j(()=>Ue($.value)),{modes:Ae}=e,L=b(Ue($.value)||Ae[0]||"rgb");function $e(){const{modes:a}=e,{value:v}=L,w=a.findIndex(y=>y===v);~w?L.value=a[(w+1)%a.length]:L.value="rgb"}let A,T,Y,Q,H,X,K,R;const ie=j(()=>{const{value:a}=$;if(!a)return null;switch(we.value){case"hsv":return he(a);case"hsl":return[A,T,Y,R]=be(a),[...ut(A,T,Y),R];case"rgb":case"hex":return[H,X,K,R]=te(a),[...qe(H,X,K),R]}}),J=j(()=>{const{value:a}=$;if(!a)return null;switch(we.value){case"rgb":case"hex":return te(a);case"hsv":return[A,T,Q,R]=he(a),[...oe(A,T,Q),R];case"hsl":return[A,T,Y,R]=be(a),[...He(A,T,Y),R]}}),ye=j(()=>{const{value:a}=$;if(!a)return null;switch(we.value){case"hsl":return be(a);case"hsv":return[A,T,Q,R]=he(a),[...Ne(A,T,Q),R];case"rgb":case"hex":return[H,X,K,R]=te(a),[...je(H,X,K),R]}}),Me=j(()=>{switch(L.value){case"rgb":case"hex":return J.value;case"hsv":return ie.value;case"hsl":return ye.value}}),ge=b(0),s=b(1),c=b([0,0]);function V(a,v){const{value:w}=ie,y=ge.value,S=w?w[3]:1;c.value=[a,v];const{showAlpha:x}=e;switch(L.value){case"hsv":C((x?pe:Xe)([y,a,v,S]),"cursor");break;case"hsl":C((x?ne:Ge)([...Ne(y,a,v),S]),"cursor");break;case"rgb":C((x?ae:Ze)([...oe(y,a,v),S]),"cursor");break;case"hex":C((x?le:Se)([...oe(y,a,v),S]),"cursor");break}}function De(a){ge.value=a;const{value:v}=ie;if(!v)return;const[,w,y,S]=v,{showAlpha:x}=e;switch(L.value){case"hsv":C((x?pe:Xe)([a,w,y,S]),"cursor");break;case"rgb":C((x?ae:Ze)([...oe(a,w,y),S]),"cursor");break;case"hex":C((x?le:Se)([...oe(a,w,y),S]),"cursor");break;case"hsl":C((x?ne:Ge)([...Ne(a,w,y),S]),"cursor");break}}function _(a){switch(L.value){case"hsv":[A,T,Q]=ie.value,C(pe([A,T,Q,a]),"cursor");break;case"rgb":[H,X,K]=J.value,C(ae([H,X,K,a]),"cursor");break;case"hex":[H,X,K]=J.value,C(le([H,X,K,a]),"cursor");break;case"hsl":[A,T,Y]=ye.value,C(ne([A,T,Y,a]),"cursor");break}s.value=a}function C(a,v){v==="cursor"?r=a:r=null;const{nTriggerFormChange:w,nTriggerFormInput:y}=l,{onUpdateValue:S,"onUpdate:value":x}=e;S&&Te(S,a),x&&Te(x,a),w(),y(),Ie.value=a}function Je(a){C(a,"input"),Wt(fe)}function fe(a=!0){const{value:v}=$;if(v){const{nTriggerFormChange:w,nTriggerFormInput:y}=l,{onComplete:S}=e;S&&S(v);const{value:x}=P,{value:N}=F;a&&(x.splice(N+1,x.length,v),F.value=N+1),w(),y()}}function yt(){const{value:a}=F;a-1<0||(C(P.value[a-1],"input"),fe(!1),F.value=a-1)}function Ct(){const{value:a}=F;a<0||a+1>=P.value.length||(C(P.value[a+1],"input"),fe(!1),F.value=a+1)}function St(){C(null,"input");const{onClear:a}=e;a&&a(),O(!1)}function _t(){const{value:a}=$,{onConfirm:v}=e;v&&v(a),O(!1)}const Ut=j(()=>F.value>=1),It=j(()=>{const{value:a}=P;return a.length>1&&F.value<a.length-1});Kt(Z,a=>{a||(P.value=[$.value],F.value=0)}),dt(()=>{if(!(r&&r===$.value)){const{value:a}=ie;a&&(ge.value=a[0],s.value=a[3],c.value=[a[1],a[2]])}r=null});const Be=j(()=>{const{value:a}=n,{common:{cubicBezierEaseInOut:v},self:{textColor:w,color:y,panelFontSize:S,boxShadow:x,border:N,borderRadius:D,dividerColor:se,[rt("height",a)]:$t,[rt("fontSize",a)]:Dt}}=I.value;return{"--n-bezier":v,"--n-text-color":w,"--n-color":y,"--n-panel-font-size":S,"--n-font-size":Dt,"--n-box-shadow":x,"--n-border":N,"--n-border-radius":D,"--n-height":$t,"--n-divider-color":se}}),ee=E?Gt("color-picker",j(()=>n.value[0]),Be,e):void 0;function At(){var a;const{value:v}=J,{value:w}=ge,{internalActions:y,modes:S,actions:x}=e,{value:N}=I,{value:D}=m;return i("div",{class:[`${D}-color-picker-panel`,ee==null?void 0:ee.themeClass.value],onDragstart:se=>{se.preventDefault()},style:E?void 0:Be.value},i("div",{class:`${D}-color-picker-control`},i(jr,{clsPrefix:D,rgba:v,displayedHue:w,displayedSv:c.value,onUpdateSV:V,onComplete:fe}),i("div",{class:`${D}-color-picker-preview`},i("div",{class:`${D}-color-picker-preview__sliders`},i(qr,{clsPrefix:D,hue:w,onUpdateHue:De,onComplete:fe}),e.showAlpha?i(Ar,{clsPrefix:D,rgba:v,alpha:s.value,onUpdateAlpha:_,onComplete:fe}):null),e.showPreview?i(Or,{clsPrefix:D,mode:L.value,color:J.value&&Se(J.value),onUpdateColor:se=>{C(se,"input")}}):null),i(Nr,{clsPrefix:D,showAlpha:e.showAlpha,mode:L.value,modes:S,onUpdateMode:$e,value:$.value,valueArr:Me.value,onUpdateValue:Je}),((a=e.swatches)===null||a===void 0?void 0:a.length)&&i(Br,{clsPrefix:D,mode:L.value,swatches:e.swatches,onUpdateColor:se=>{C(se,"input")}})),x!=null&&x.length?i("div",{class:`${D}-color-picker-action`},x.includes("confirm")&&i(re,{size:"small",onClick:_t,theme:N.peers.Button,themeOverrides:N.peerOverrides.Button},{default:()=>d.value.confirm}),x.includes("clear")&&i(re,{size:"small",onClick:St,disabled:!$.value,theme:N.peers.Button,themeOverrides:N.peerOverrides.Button},{default:()=>d.value.clear})):null,t.action?i("div",{class:`${D}-color-picker-action`},{default:t.action}):y?i("div",{class:`${D}-color-picker-action`},y.includes("undo")&&i(re,{size:"small",onClick:yt,disabled:!Ut.value,theme:N.peers.Button,themeOverrides:N.peerOverrides.Button},{default:()=>d.value.undo}),y.includes("redo")&&i(re,{size:"small",onClick:Ct,disabled:!It.value,theme:N.peers.Button,themeOverrides:N.peerOverrides.Button},{default:()=>d.value.redo})):null)}return{mergedClsPrefix:m,namespace:g,selfRef:o,hsla:ye,rgba:J,mergedShow:Z,mergedDisabled:u,isMounted:Zt(),adjustedTo:We(e),mergedValue:$,handleTriggerClick(){O(!0)},handleClickOutside(a){var v;!((v=o.value)===null||v===void 0)&&v.contains(Xt(a))||O(!1)},renderPanel:At,cssVars:E?void 0:Be,themeClass:ee==null?void 0:ee.themeClass,onRender:ee==null?void 0:ee.onRender}},render(){const{mergedClsPrefix:e,onRender:t}=this;return t==null||t(),i("div",{class:[this.themeClass,`${e}-color-picker`],ref:"selfRef",style:this.cssVars},i(Nt,null,{default:()=>[i(zt,null,{default:()=>i(Er,{clsPrefix:e,value:this.mergedValue,hsla:this.hsla,disabled:this.mergedDisabled,onClick:this.handleTriggerClick})}),i(Mt,{placement:this.placement,show:this.mergedShow,containerClass:this.namespace,teleportDisabled:this.adjustedTo===We.tdkey,to:this.adjustedTo},{default:()=>i(Bt,{name:"fade-in-scale-up-transition",appear:this.isMounted},{default:()=>this.mergedShow?Et(this.renderPanel(),[[Ot,this.handleClickOutside,void 0,{capture:!0}]]):null})})]}))}}),Xr={xmlns:"http://www.w3.org/2000/svg","xmlns:xlink":"http://www.w3.org/1999/xlink",viewBox:"0 0 512 512"},Wr=B({name:"AppsOutline",render:function(t,o){return M(),G("svg",Xr,o[0]||(o[0]=[pt('<rect x="64" y="64" width="80" height="80" rx="40" ry="40" fill="none" stroke="currentColor" stroke-miterlimit="10" stroke-width="32"></rect><rect x="216" y="64" width="80" height="80" rx="40" ry="40" fill="none" stroke="currentColor" stroke-miterlimit="10" stroke-width="32"></rect><rect x="368" y="64" width="80" height="80" rx="40" ry="40" fill="none" stroke="currentColor" stroke-miterlimit="10" stroke-width="32"></rect><rect x="64" y="216" width="80" height="80" rx="40" ry="40" fill="none" stroke="currentColor" stroke-miterlimit="10" stroke-width="32"></rect><rect x="216" y="216" width="80" height="80" rx="40" ry="40" fill="none" stroke="currentColor" stroke-miterlimit="10" stroke-width="32"></rect><rect x="368" y="216" width="80" height="80" rx="40" ry="40" fill="none" stroke="currentColor" stroke-miterlimit="10" stroke-width="32"></rect><rect x="64" y="368" width="80" height="80" rx="40" ry="40" fill="none" stroke="currentColor" stroke-miterlimit="10" stroke-width="32"></rect><rect x="216" y="368" width="80" height="80" rx="40" ry="40" fill="none" stroke="currentColor" stroke-miterlimit="10" stroke-width="32"></rect><rect x="368" y="368" width="80" height="80" rx="40" ry="40" fill="none" stroke="currentColor" stroke-miterlimit="10" stroke-width="32"></rect>',9)]))}}),Yr={xmlns:"http://www.w3.org/2000/svg","xmlns:xlink":"http://www.w3.org/1999/xlink",viewBox:"0 0 512 512"},Qr=B({name:"ListOutline",render:function(t,o){return M(),G("svg",Yr,o[0]||(o[0]=[pt('<path fill="none" stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="32" d="M160 144h288"></path><path fill="none" stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="32" d="M160 256h288"></path><path fill="none" stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="32" d="M160 368h288"></path><circle cx="80" cy="144" r="16" fill="none" stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="32"></circle><circle cx="80" cy="256" r="16" fill="none" stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="32"></circle><circle cx="80" cy="368" r="16" fill="none" stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="32"></circle>',6)]))}}),Jr={style:{"margin-right":"-10px"}},ea={class:"description"},ta={key:0},ra={key:0},aa=B({__name:"TagListItem",props:{tag:{},isChecked:{type:Boolean}},emits:["onEditTag","onDeleteTag","onChecked","onUnChecked"],setup(e,{emit:t}){const o=e,r=gt(),l=t,n=g=>{l("onEditTag",g)},u=g=>{l("onDeleteTag",g)},d=g=>{l(g?"onChecked":"onUnChecked",o.tag)},m=()=>{bt.push({name:kt.POST.name,query:{tagId:o.tag.tagId}})};return(g,E)=>(M(),ft(h(rr),null,{default:f(()=>[p(h(Yt),{class:"animate__animated animate__fadeIn"},{avatar:f(()=>[ze("div",Jr,[p(h(tr),{checked:g.isChecked,style:{"margin-left":"-4px","margin-right":"12px"},onUpdateChecked:d},null,8,["checked"])])]),header:f(()=>[p(h(yr),{class:"none-select pointer",value:g.tag.postCount??0,type:"info",onClick:m},{default:f(()=>[p(xt,{size:"medium",tag:g.tag},null,8,["tag"])]),_:1},8,["value"])]),description:f(()=>[ze("div",ea,[p(h(Jt),{depth:"3"},{default:f(()=>[er(mt(g.tag.slug),1)]),_:1})])]),"header-extra":f(()=>[p(h(wr),{size:"small"},{default:f(()=>[p(h(re),{type:"default",tertiary:"",onClick:E[0]||(E[0]=I=>n(g.tag))},{icon:f(()=>[p(h(Ye),null,{default:f(()=>[p(h(Qt))]),_:1})]),default:f(()=>[h(r).isSmallWindow?_e("",!0):(M(),G("span",ta,"设置"))]),_:1}),p(h(re),{type:"error",tertiary:"",onClick:E[1]||(E[1]=I=>u(g.tag))},{icon:f(()=>[p(h(Ye),null,{default:f(()=>[p(h(vt))]),_:1})]),default:f(()=>[h(r).isSmallWindow?_e("",!0):(M(),G("span",ra,"删除"))]),_:1})]),_:1})]),_:1})]),_:1}))}});var z=(e=>(e[e.LIST=0]="LIST",e[e.BLOCK=1]="BLOCK",e))(z||{});const oa={class:"container"},na={key:0},la={key:0,style:{padding:"10px"}},ia=["onClick"],sa={key:1},pa=B({__name:"TagView",setup(e){const t=gt(),o=b(z.BLOCK),r=b(null),l=b(1),n=b(10),u=b(0),d=b(0),m=b(-1),g=b(Array()),E=[{label:"查看文章",key:"viewPost",icon:Fe(fr)},{type:"divider"},{label:"编辑标签",key:"edit",icon:Fe(vr)},{label:"删除标签",key:"delete",icon:Fe(vt)}],I=b(ue.ADD),W=b(null),Z=b(!1),O=b(!1),k=ar({tagId:-1,displayName:"",slug:"",color:""}),Ie=nr();or(()=>{$();let s=Number(Ie.query.tagId);isNaN(s)||mr(Number(s)).then(c=>{A(c.data)}).catch(()=>{}),P()});const $=()=>{let s=Number(localStorage.getItem(Re.TAG_MODE.toString())??z.BLOCK);Object.values(z).includes(s)?o.value=s:o.value=z.BLOCK;let c=Number(localStorage.getItem(Re.TAG_PAGE_SIZE.toString())??10);isNaN(c)||c<10||c>120?n.value=10:n.value=c},P=()=>{window.$loadingBar.start(),o.value===z.BLOCK?it().then(s=>{var c;r.value=s.data.data,u.value=((c=r.value)==null?void 0:c.length)??0,window.$loadingBar.finish()}).catch(()=>{window.$loadingBar.error()}):F()},F=()=>{it(l.value,n.value).then(s=>{var V;let c=s.data;if(((V=c.data)==null?void 0:V.length)===0&&c.totalData!==0){l.value=1,F();return}r.value=c.data,u.value=c.totalData,d.value=c.totalPages,window.$loadingBar.finish()}).catch(()=>{window.$loadingBar.error()})},we=s=>{var V;let c=(V=r.value)==null?void 0:V[m.value];switch(s){case"delete":Ae(c);break;case"edit":A(c);break;case"viewPost":bt.push({name:kt.POST.name,query:{tagId:c.tagId}});break}},Ae=s=>{lt("确定要删除标签 ["+(s==null?void 0:s.displayName)+"] 吗？此操作不可逆。",()=>{$e([s.tagId])})},L=()=>{let s=Array();g.value.forEach(c=>{s.push(c)}),lt(`确定要删除选择的 ${s.length} 个标签吗？`,()=>{$e(s)})},$e=s=>{br(s).then(()=>{Le("删除成功"),g.value=g.value.filter(c=>!s.includes(c)),P()}).catch(()=>{})},A=s=>{H(),k.tagId=s.tagId,k.displayName=s.displayName,k.slug=s.slug,k.color=s.color??"",I.value=ue.EDIT,Z.value=!0},T=()=>{H(),I.value=ue.ADD,Z.value=!0},Y=()=>{var s;return(s=W.value)==null||s.validate(c=>{if(!c){O.value=!0;let V={tagId:k.tagId,displayName:k.displayName,slug:encodeURIComponent(k.slug),color:k.color};I.value===ue.ADD?kr(V).then(()=>{Le("添加成功"),Q()}).catch(()=>{O.value=!1}):xr(V).then(()=>{Le("修改成功"),Q()}).catch(()=>{O.value=!1})}}).catch(()=>{}),!1},Q=()=>{O.value=!1,Z.value=!1,H(),P()},H=()=>{k.tagId=-1,k.displayName="",k.slug="",k.color=""},X=s=>{k.displayName=s,I.value===ue.ADD&&(k.slug=hr(s))},K=s=>{o.value=s,P(),localStorage.setItem(Re.TAG_MODE.toString(),s.toString())},R=s=>{l.value=s,P()},ie=s=>{n.value=s,localStorage.setItem(Re.TAG_PAGE_SIZE.toString(),s.toString()),P()},J=s=>{g.value.push(s.tagId)},ye=s=>{g.value=g.value.filter(c=>c!==s.tagId)},Me=()=>{var s;g.value=[],(s=r.value)==null||s.forEach(c=>{g.value.push(c.tagId)})},ge=()=>{g.value=[]};return(s,c)=>{var V,De;return M(),G("div",oa,[p(h(ir),{show:Z.value,"onUpdate:show":c[3]||(c[3]=_=>Z.value=_),preset:"dialog",title:I.value==h(ue).ADD?"添加标签":"标签设置","positive-text":I.value==h(ue).ADD?"添加":"保存","negative-text":"取消",loading:O.value,onPositiveClick:Y,onKeydown:lr(Y,["enter"])},{default:f(()=>[p(h(ur),{ref_key:"addEditDialogRef",ref:W,class:"dialog-form",model:k},{default:f(()=>[p(h(Oe),{label:"标签名",path:"displayName",rule:{required:!0,message:"请输入标签名"}},{default:f(()=>[p(h(Ke),{"default-value":k.displayName,"onUpdate:defaultValue":c[0]||(c[0]=_=>k.displayName=_),placeholder:"标签名",onUpdateValue:X,maxlength:"50"},null,8,["default-value"])]),_:1}),p(h(Oe),{label:"别名",path:"slug",rule:{required:!0,message:"请输入标签别名"}},{default:f(()=>[p(h(Ke),{value:k.slug,"onUpdate:value":c[1]||(c[1]=_=>k.slug=_),placeholder:"标签别名",maxlength:"50"},null,8,["value"])]),_:1}),p(h(Oe),{label:"颜色",path:"color"},{default:f(()=>[p(h(Zr),{value:k.color,"onUpdate:value":c[2]||(c[2]=_=>k.color=_),actions:["clear"],modes:["hex"],"show-alpha":!1},null,8,["value"])]),_:1})]),_:1},8,["model"])]),_:1},8,["show","title","positive-text","loading"]),p(sr,{"data-count":u.value,"show-empty-status":r.value!==null&&r.value.length===0,"current-page":l.value,"page-size":n.value,"page-count":d.value,"current-page-item-count":((V=r.value)==null?void 0:V.length)??0,"show-pagination":o.value==h(z).LIST,"item-string":"标签",onOnPageUpdate:R,onOnPageSizeUpdate:ie,"show-checkbox":"",onOnChecked:Me,onOnCheckboxCancel:ge,"is-checked":g.value.length===((De=r.value)==null?void 0:De.length)&&r.value.length!==0,"show-delete-button":g.value.length>0,onOnDeleteButtonClick:L},{"header-extra":f(()=>[p(h(re),{type:"primary",onClick:T},{icon:f(()=>[p(h(Ye),null,{default:f(()=>[p(h(dr))]),_:1})]),default:f(()=>[h(t).isSmallWindow?_e("",!0):(M(),G("span",na,"添加标签"))]),_:1})]),content:f(()=>[o.value===h(z).BLOCK?(M(),G("div",la,[p(h(at),null,{default:f(()=>[(M(!0),G(ot,null,nt(r.value,(_,C)=>(M(),G("div",{onClick:Je=>m.value=C},[p(h(pr),{trigger:"hover","keep-alive-on-hover":!1},{trigger:f(()=>[ze("div",null,[p(h(gr),{trigger:"click",options:E,"show-arrow":"",onSelect:we},{default:f(()=>[p(xt,{size:"medium",tag:_,pointer:""},null,8,["tag"])]),_:2},1024)])]),default:f(()=>[ze("span",null,mt(_.slug),1)]),_:2},1024)],8,ia))),256))]),_:1})])):_e("",!0),o.value===h(z).LIST?(M(),G("div",sa,[p(h(cr),{hoverable:""},{default:f(()=>[(M(!0),G(ot,null,nt(r.value,_=>(M(),ft(aa,{tag:_,"is-checked":g.value.includes(_.tagId),onOnDeleteTag:Ae,onOnEditTag:A,onOnChecked:J,onOnUnChecked:ye},null,8,["tag","is-checked"]))),256))]),_:1})])):_e("",!0)]),"action-left":f(()=>[p(h(at),null,{default:f(()=>[p(h(re),{class:"btn-switch-mode",circle:"",size:"small",secondary:o.value===h(z).BLOCK,onClick:c[4]||(c[4]=_=>K(h(z).BLOCK))},{icon:f(()=>[p(h(Wr))]),_:1},8,["secondary"]),p(h(re),{class:"btn-switch-mode",circle:"",size:"small",secondary:o.value===h(z).LIST,onClick:c[5]||(c[5]=_=>K(h(z).LIST))},{icon:f(()=>[p(h(Qr))]),_:1},8,["secondary"])]),_:1})]),_:1},8,["data-count","show-empty-status","current-page","page-size","page-count","current-page-item-count","show-pagination","is-checked","show-delete-button"])])}}});export{pa as default};
