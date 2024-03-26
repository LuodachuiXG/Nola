import{az as ne,aA as ee,aB as le,aC as he,aD as ae,aE as ke,aF as pe,d as z,e as b,W as i,aG as xe,aH as we,a6 as F,aI as $t,D as Ne,aJ as ut,p as Le,aK as _e,aL as je,aM as Ge,aN as Ke,aO as Dt,aP as ve,a1 as A,aQ as It,aR as B,a3 as et,aS as Mt,aT as Vt,aU as Rt,a5 as ct,ah as Tt,ai as Fe,aV as tt,E as Nt,aW as Pt,aX as zt,aY as Ze,aZ as Et,a_ as Bt,a$ as Ft,b0 as Ht,b1 as Ot,b2 as qt,b3 as Lt,b4 as jt,b5 as De,b6 as at,N as te,ad as Gt,o as P,c as q,C as dt,k as ht,w as f,a as g,A as Te,u as h,b as Kt,G as pt,H as Xe,T as Zt,m as Ue,f as Xt,g as Wt,S as Ie,i as me,q as Yt,ao as Jt,X as rt,j as He,x as Qt,v as Oe,V as ea,M as ot,O as nt,P as lt,F as ta,ar as aa,au as qe,ax as ra,b7 as oa}from"./index-BeAjLgC_.js";import{_ as gt,b as na,t as it,a as la,u as ia,d as sa}from"./tagApi-3Xw9A7Aw.js";import{D as ue}from"./DialogFormMode-F5uswgu2.js";import{N as ua,T as ft}from"./TrashOutline-DLK0KsOh.js";import{N as ca,a as da,b as ha,c as pa,_ as ga,A as fa,d as va}from"./MyCard.vue_vue_type_script_setup_true_lang-DGQyIiRj.js";import{N as ma}from"./Badge-Dfbpssef.js";import{N as ba}from"./InputGroup-BbfXAoJf.js";import{E as ka}from"./BrushOutline-YOyJEL-w.js";import"./Result-BUcOHWzf.js";function vt(e,a,r){a/=100,r/=100;const t=a*Math.min(r,1-r)+r;return[e,t?(2-2*r/t)*100:0,t*100]}function Re(e,a,r){a/=100,r/=100;const t=r-r*a/2,o=Math.min(t,1-t);return[e,o?(r-t)/o*100:0,t*100]}function oe(e,a,r){a/=100,r/=100;let t=(o,n=(o+e/60)%6)=>r-r*a*Math.max(Math.min(n,4-n,1),0);return[t(5)*255,t(3)*255,t(1)*255]}function We(e,a,r){e/=255,a/=255,r/=255;let t=Math.max(e,a,r),o=t-Math.min(e,a,r),n=o&&(t==e?(a-r)/o:t==a?2+(r-e)/o:4+(e-a)/o);return[60*(n<0?n+6:n),t&&o/t*100,t*100]}function Ye(e,a,r){e/=255,a/=255,r/=255;let t=Math.max(e,a,r),o=t-Math.min(e,a,r),n=1-Math.abs(t+t-o-1),u=o&&(t==e?(a-r)/o:t==a?2+(r-e)/o:4+(e-a)/o);return[60*(u<0?u+6:u),n?o/n*100:0,(t+t-o)*50]}function Je(e,a,r){a/=100,r/=100;let t=a*Math.min(r,1-r),o=(n,u=(n+e/30)%12)=>r-t*Math.max(Math.min(u-3,9-u,1),-1);return[o(0)*255,o(8)*255,o(4)*255]}function xa(e,a){switch(e[0]){case"hex":return a?"#000000FF":"#000000";case"rgb":return a?"rgba(0, 0, 0, 1)":"rgb(0, 0, 0)";case"hsl":return a?"hsla(0, 0%, 0%, 1)":"hsl(0, 0%, 0%)";case"hsv":return a?"hsva(0, 0%, 0%, 1)":"hsv(0, 0%, 0%)"}return"#000000"}function Ae(e){return e===null?null:/^ *#/.test(e)?"hex":e.includes("rgb")?"rgb":e.includes("hsl")?"hsl":e.includes("hsv")?"hsv":null}function wa(e){return e=Math.round(e),e>=360?359:e<0?0:e}function ya(e){return e=Math.round(e*100)/100,e>1?1:e<0?0:e}const Ca={rgb:{hex(e){return ne(ee(e))},hsl(e){const[a,r,t,o]=ee(e);return le([...Ye(a,r,t),o])},hsv(e){const[a,r,t,o]=ee(e);return he([...We(a,r,t),o])}},hex:{rgb(e){return ae(ee(e))},hsl(e){const[a,r,t,o]=ee(e);return le([...Ye(a,r,t),o])},hsv(e){const[a,r,t,o]=ee(e);return he([...We(a,r,t),o])}},hsl:{hex(e){const[a,r,t,o]=ke(e);return ne([...Je(a,r,t),o])},rgb(e){const[a,r,t,o]=ke(e);return ae([...Je(a,r,t),o])},hsv(e){const[a,r,t,o]=ke(e);return he([...vt(a,r,t),o])}},hsv:{hex(e){const[a,r,t,o]=pe(e);return ne([...oe(a,r,t),o])},rgb(e){const[a,r,t,o]=pe(e);return ae([...oe(a,r,t),o])},hsl(e){const[a,r,t,o]=pe(e);return le([...Re(a,r,t),o])}}};function mt(e,a,r){return r=r||Ae(e),r?r===a?e:Ca[r][a](e):null}const be="12px",Sa=12,ce="6px",_a=6,Ua="linear-gradient(90deg,red,#ff0 16.66%,#0f0 33.33%,#0ff 50%,#00f 66.66%,#f0f 83.33%,red)",Aa=z({name:"HueSlider",props:{clsPrefix:{type:String,required:!0},hue:{type:Number,required:!0},onUpdateHue:{type:Function,required:!0},onComplete:Function},setup(e){const a=b(null);function r(n){a.value&&(xe("mousemove",document,t),xe("mouseup",document,o),t(n))}function t(n){const{value:u}=a;if(!u)return;const{width:c,left:p}=u.getBoundingClientRect(),m=wa((n.clientX-p-_a)/(c-Sa)*360);e.onUpdateHue(m)}function o(){var n;we("mousemove",document,t),we("mouseup",document,o),(n=e.onComplete)===null||n===void 0||n.call(e)}return{railRef:a,handleMouseDown:r}},render(){const{clsPrefix:e}=this;return i("div",{class:`${e}-color-picker-slider`,style:{height:be,borderRadius:ce}},i("div",{ref:"railRef",style:{boxShadow:"inset 0 0 2px 0 rgba(0, 0, 0, .24)",boxSizing:"border-box",backgroundImage:Ua,height:be,borderRadius:ce,position:"relative"},onMousedown:this.handleMouseDown},i("div",{style:{position:"absolute",left:ce,right:ce,top:0,bottom:0}},i("div",{class:`${e}-color-picker-handle`,style:{left:`calc((${this.hue}%) / 359 * 100 - ${ce})`,borderRadius:ce,width:be,height:be}},i("div",{class:`${e}-color-picker-handle__fill`,style:{backgroundColor:`hsl(${this.hue}, 100%, 50%)`,borderRadius:ce,width:be,height:be}})))))}}),Se="12px",$a=12,de="6px",Da=z({name:"AlphaSlider",props:{clsPrefix:{type:String,required:!0},rgba:{type:Array,default:null},alpha:{type:Number,default:0},onUpdateAlpha:{type:Function,required:!0},onComplete:Function},setup(e){const a=b(null);function r(n){!a.value||!e.rgba||(xe("mousemove",document,t),xe("mouseup",document,o),t(n))}function t(n){const{value:u}=a;if(!u)return;const{width:c,left:p}=u.getBoundingClientRect(),m=(n.clientX-p)/(c-$a);e.onUpdateAlpha(ya(m))}function o(){var n;we("mousemove",document,t),we("mouseup",document,o),(n=e.onComplete)===null||n===void 0||n.call(e)}return{railRef:a,railBackgroundImage:F(()=>{const{rgba:n}=e;return n?`linear-gradient(to right, rgba(${n[0]}, ${n[1]}, ${n[2]}, 0) 0%, rgba(${n[0]}, ${n[1]}, ${n[2]}, 1) 100%)`:""}),handleMouseDown:r}},render(){const{clsPrefix:e}=this;return i("div",{class:`${e}-color-picker-slider`,ref:"railRef",style:{height:Se,borderRadius:de},onMousedown:this.handleMouseDown},i("div",{style:{borderRadius:de,position:"absolute",left:0,right:0,top:0,bottom:0,overflow:"hidden"}},i("div",{class:`${e}-color-picker-checkboard`}),i("div",{class:`${e}-color-picker-slider__image`,style:{backgroundImage:this.railBackgroundImage}})),this.rgba&&i("div",{style:{position:"absolute",left:de,right:de,top:0,bottom:0}},i("div",{class:`${e}-color-picker-handle`,style:{left:`calc(${this.alpha*100}% - ${de})`,borderRadius:de,width:Se,height:Se}},i("div",{class:`${e}-color-picker-handle__fill`,style:{backgroundColor:ae(this.rgba),borderRadius:de,width:Se,height:Se}}))))}}),Me="12px",Ve="6px",Ia=z({name:"Pallete",props:{clsPrefix:{type:String,required:!0},rgba:{type:Array,default:null},displayedHue:{type:Number,required:!0},displayedSv:{type:Array,required:!0},onUpdateSV:{type:Function,required:!0},onComplete:Function},setup(e){const a=b(null);function r(n){a.value&&(xe("mousemove",document,t),xe("mouseup",document,o),t(n))}function t(n){const{value:u}=a;if(!u)return;const{width:c,height:p,left:m,bottom:Z}=u.getBoundingClientRect(),M=(Z-n.clientY)/p,X=(n.clientX-m)/c,L=100*(X>1?1:X<0?0:X),E=100*(M>1?1:M<0?0:M);e.onUpdateSV(L,E)}function o(){var n;we("mousemove",document,t),we("mouseup",document,o),(n=e.onComplete)===null||n===void 0||n.call(e)}return{palleteRef:a,handleColor:F(()=>{const{rgba:n}=e;return n?`rgb(${n[0]}, ${n[1]}, ${n[2]})`:""}),handleMouseDown:r}},render(){const{clsPrefix:e}=this;return i("div",{class:`${e}-color-picker-pallete`,onMousedown:this.handleMouseDown,ref:"palleteRef"},i("div",{class:`${e}-color-picker-pallete__layer`,style:{backgroundImage:`linear-gradient(90deg, white, hsl(${this.displayedHue}, 100%, 50%))`}}),i("div",{class:`${e}-color-picker-pallete__layer ${e}-color-picker-pallete__layer--shadowed`,style:{backgroundImage:"linear-gradient(180deg, rgba(0, 0, 0, 0%), rgba(0, 0, 0, 100%))"}}),this.rgba&&i("div",{class:`${e}-color-picker-handle`,style:{width:Me,height:Me,borderRadius:Ve,left:`calc(${this.displayedSv[0]}% - ${Ve})`,bottom:`calc(${this.displayedSv[1]}% - ${Ve})`}},i("div",{class:`${e}-color-picker-handle__fill`,style:{backgroundColor:this.handleColor,borderRadius:Ve,width:Me,height:Me}})))}}),Qe=$t("n-color-picker");function Ma(e){return/^\d{1,3}\.?\d*$/.test(e.trim())?Math.max(0,Math.min(parseInt(e),255)):!1}function Va(e){return/^\d{1,3}\.?\d*$/.test(e.trim())?Math.max(0,Math.min(parseInt(e),360)):!1}function Ra(e){return/^\d{1,3}\.?\d*$/.test(e.trim())?Math.max(0,Math.min(parseInt(e),100)):!1}function Ta(e){const a=e.trim();return/^#[0-9a-fA-F]+$/.test(a)?[4,5,7,9].includes(a.length):!1}function Na(e){return/^\d{1,3}\.?\d*%$/.test(e.trim())?Math.max(0,Math.min(parseInt(e)/100,100)):!1}const Pa={paddingSmall:"0 4px"},st=z({name:"ColorInputUnit",props:{label:{type:String,required:!0},value:{type:[Number,String],default:null},showAlpha:Boolean,onUpdateValue:{type:Function,required:!0}},setup(e){const a=b(""),{themeRef:r}=Ne(Qe,null);ut(()=>{a.value=t()});function t(){const{value:u}=e;if(u===null)return"";const{label:c}=e;return c==="HEX"?u:c==="A"?`${Math.floor(u*100)}%`:String(Math.floor(u))}function o(u){a.value=u}function n(u){let c,p;switch(e.label){case"HEX":p=Ta(u),p&&e.onUpdateValue(u),a.value=t();break;case"H":c=Va(u),c===!1?a.value=t():e.onUpdateValue(c);break;case"S":case"L":case"V":c=Ra(u),c===!1?a.value=t():e.onUpdateValue(c);break;case"A":c=Na(u),c===!1?a.value=t():e.onUpdateValue(c);break;case"R":case"G":case"B":c=Ma(u),c===!1?a.value=t():e.onUpdateValue(c);break}}return{mergedTheme:r,inputValue:a,handleInputChange:n,handleInputUpdateValue:o}},render(){const{mergedTheme:e}=this;return i(Le,{size:"small",placeholder:this.label,theme:e.peers.Input,themeOverrides:e.peerOverrides.Input,builtinThemeOverrides:Pa,value:this.inputValue,onUpdateValue:this.handleInputUpdateValue,onChange:this.handleInputChange,style:this.label==="A"?"flex-grow: 1.25;":""})}}),za=z({name:"ColorInput",props:{clsPrefix:{type:String,required:!0},mode:{type:String,required:!0},modes:{type:Array,required:!0},showAlpha:{type:Boolean,required:!0},value:{type:String,default:null},valueArr:{type:Array,default:null},onUpdateValue:{type:Function,required:!0},onUpdateMode:{type:Function,required:!0}},setup(e){return{handleUnitUpdateValue(a,r){const{showAlpha:t}=e;if(e.mode==="hex"){e.onUpdateValue((t?ne:_e)(r));return}let o;switch(e.valueArr===null?o=[0,0,0,0]:o=Array.from(e.valueArr),e.mode){case"hsv":o[a]=r,e.onUpdateValue((t?he:Ke)(o));break;case"rgb":o[a]=r,e.onUpdateValue((t?ae:Ge)(o));break;case"hsl":o[a]=r,e.onUpdateValue((t?le:je)(o));break}}}},render(){const{clsPrefix:e,modes:a}=this;return i("div",{class:`${e}-color-picker-input`},i("div",{class:`${e}-color-picker-input__mode`,onClick:this.onUpdateMode,style:{cursor:a.length===1?"":"pointer"}},this.mode.toUpperCase()+(this.showAlpha?"A":"")),i(ba,null,{default:()=>{const{mode:r,valueArr:t,showAlpha:o}=this;if(r==="hex"){let n=null;try{n=t===null?null:(o?ne:_e)(t)}catch{}return i(st,{label:"HEX",showAlpha:o,value:n,onUpdateValue:u=>{this.handleUnitUpdateValue(0,u)}})}return(r+(o?"a":"")).split("").map((n,u)=>i(st,{label:n.toUpperCase(),value:t===null?null:t[u],onUpdateValue:c=>{this.handleUnitUpdateValue(u,c)}}))}}))}}),Ea=z({name:"ColorPickerTrigger",props:{clsPrefix:{type:String,required:!0},value:{type:String,default:null},hsla:{type:Array,default:null},disabled:Boolean,onClick:Function},setup(e){const{colorPickerSlots:a,renderLabelRef:r}=Ne(Qe,null);return()=>{const{hsla:t,value:o,clsPrefix:n,onClick:u,disabled:c}=e,p=a.label||r.value;return i("div",{class:[`${n}-color-picker-trigger`,c&&`${n}-color-picker-trigger--disabled`],onClick:c?void 0:u},i("div",{class:`${n}-color-picker-trigger__fill`},i("div",{class:`${n}-color-picker-checkboard`}),i("div",{style:{position:"absolute",left:0,right:0,top:0,bottom:0,backgroundColor:t?le(t):""}}),o&&t?i("div",{class:`${n}-color-picker-trigger__value`,style:{color:t[2]>50||t[3]<.5?"black":"white"}},p?p(o):o):null))}}});function Ba(e,a){if(a==="hsv"){const[r,t,o,n]=pe(e);return ae([...oe(r,t,o),n])}return e}function Fa(e){const a=document.createElement("canvas").getContext("2d");return a?(a.fillStyle=e,a.fillStyle):"#000000"}const Ha=z({name:"ColorPickerSwatches",props:{clsPrefix:{type:String,required:!0},mode:{type:String,required:!0},swatches:{type:Array,required:!0},onUpdateColor:{type:Function,required:!0}},setup(e){const a=F(()=>e.swatches.map(n=>{const u=Ae(n);return{value:n,mode:u,legalValue:Ba(n,u)}}));function r(n){const{mode:u}=e;let{value:c,mode:p}=n;return p||(p="hex",/^[a-zA-Z]+$/.test(c)?c=Fa(c):(Dt("color-picker",`color ${c} in swatches is invalid.`),c="#000000")),p===u?c:mt(c,u,p)}function t(n){e.onUpdateColor(r(n))}function o(n,u){n.key==="Enter"&&t(u)}return{parsedSwatchesRef:a,handleSwatchSelect:t,handleSwatchKeyDown:o}},render(){const{clsPrefix:e}=this;return i("div",{class:`${e}-color-picker-swatches`},this.parsedSwatchesRef.map(a=>i("div",{class:`${e}-color-picker-swatch`,tabindex:0,onClick:()=>{this.handleSwatchSelect(a)},onKeydown:r=>{this.handleSwatchKeyDown(r,a)}},i("div",{class:`${e}-color-picker-swatch__fill`,style:{background:a.legalValue}}))))}}),Oa=z({name:"ColorPreview",props:{clsPrefix:{type:String,required:!0},mode:{type:String,required:!0},color:{type:String,default:null,validator:e=>{const a=Ae(e);return!!(!e||a&&a!=="hsv")}},onUpdateColor:{type:Function,required:!0}},setup(e){function a(r){var t;const o=r.target.value;(t=e.onUpdateColor)===null||t===void 0||t.call(e,mt(o.toUpperCase(),e.mode,"hex")),r.stopPropagation()}return{handleChange:a}},render(){const{clsPrefix:e}=this;return i("div",{class:`${e}-color-picker-preview__preview`},i("span",{class:`${e}-color-picker-preview__fill`,style:{background:this.color||"#000000"}}),i("input",{class:`${e}-color-picker-preview__input`,type:"color",value:this.color,onChange:this.handleChange}))}}),qa=ve([A("color-picker",`
 display: inline-block;
 box-sizing: border-box;
 height: var(--n-height);
 font-size: var(--n-font-size);
 width: 100%;
 position: relative;
 `),A("color-picker-panel",`
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
 `,[It(),A("input",`
 text-align: center;
 `)]),A("color-picker-checkboard",`
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
 `)]),A("color-picker-slider",`
 margin-bottom: 8px;
 position: relative;
 box-sizing: border-box;
 `,[B("image",`
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
 `)]),A("color-picker-handle",`
 z-index: 1;
 box-shadow: 0 0 2px 0 rgba(0, 0, 0, .45);
 position: absolute;
 background-color: white;
 overflow: hidden;
 `,[B("fill",`
 box-sizing: border-box;
 border: 2px solid white;
 `)]),A("color-picker-pallete",`
 height: 180px;
 position: relative;
 margin-bottom: 8px;
 cursor: crosshair;
 `,[B("layer",`
 position: absolute;
 left: 0;
 right: 0;
 top: 0;
 bottom: 0;
 `,[et("shadowed",`
 box-shadow: inset 0 0 2px 0 rgba(0, 0, 0, .24);
 `)])]),A("color-picker-preview",`
 display: flex;
 `,[B("sliders",`
 flex: 1 0 auto;
 `),B("preview",`
 position: relative;
 height: 30px;
 width: 30px;
 margin: 0 0 8px 6px;
 border-radius: 50%;
 box-shadow: rgba(0, 0, 0, .15) 0px 0px 0px 1px inset;
 overflow: hidden;
 `),B("fill",`
 display: block;
 width: 30px;
 height: 30px;
 `),B("input",`
 position: absolute;
 top: 0;
 left: 0;
 width: 30px;
 height: 30px;
 opacity: 0;
 z-index: 1;
 `)]),A("color-picker-input",`
 display: flex;
 align-items: center;
 `,[A("input",`
 flex-grow: 1;
 flex-basis: 0;
 `),B("mode",`
 width: 72px;
 text-align: center;
 `)]),A("color-picker-control",`
 padding: 12px;
 `),A("color-picker-action",`
 display: flex;
 margin-top: -4px;
 border-top: 1px solid var(--n-divider-color);
 padding: 8px 12px;
 justify-content: flex-end;
 `,[A("button","margin-left: 8px;")]),A("color-picker-trigger",`
 border: var(--n-border);
 height: 100%;
 box-sizing: border-box;
 border-radius: var(--n-border-radius);
 transition: border-color .3s var(--n-bezier);
 cursor: pointer;
 `,[B("value",`
 white-space: nowrap;
 position: relative;
 `),B("fill",`
 border-radius: var(--n-border-radius);
 position: absolute;
 display: flex;
 align-items: center;
 justify-content: center;
 left: 4px;
 right: 4px;
 top: 4px;
 bottom: 4px;
 `),et("disabled","cursor: not-allowed"),A("color-picker-checkboard",`
 border-radius: var(--n-border-radius);
 `,[ve("&::after",`
 --n-block-size: calc((var(--n-height) - 8px) / 3);
 background-size: calc(var(--n-block-size) * 2) calc(var(--n-block-size) * 2);
 background-position: 0 0, 0 var(--n-block-size), var(--n-block-size) calc(-1 * var(--n-block-size)), calc(-1 * var(--n-block-size)) 0px; 
 `)])]),A("color-picker-swatches",`
 display: grid;
 grid-gap: 8px;
 flex-wrap: wrap;
 position: relative;
 grid-template-columns: repeat(auto-fill, 18px);
 margin-top: 10px;
 `,[A("color-picker-swatch",`
 width: 18px;
 height: 18px;
 background-image: linear-gradient(45deg, #DDD 25%, #0000 25%), linear-gradient(-45deg, #DDD 25%, #0000 25%), linear-gradient(45deg, #0000 75%, #DDD 75%), linear-gradient(-45deg, #0000 75%, #DDD 75%);
 background-size: 8px 8px;
 background-position: 0px 0, 0px 4px, 4px -4px, -4px 0px;
 background-repeat: repeat;
 `,[B("fill",`
 position: relative;
 width: 100%;
 height: 100%;
 border-radius: 3px;
 box-shadow: rgba(0, 0, 0, .15) 0px 0px 0px 1px inset;
 cursor: pointer;
 `),ve("&:focus",`
 outline: none;
 `,[B("fill",[ve("&::after",`
 position: absolute;
 top: 0;
 right: 0;
 bottom: 0;
 left: 0;
 background: inherit;
 filter: blur(2px);
 content: "";
 `)])])])])]),La=Object.assign(Object.assign({},ct.props),{value:String,show:{type:Boolean,default:void 0},defaultShow:Boolean,defaultValue:String,modes:{type:Array,default:()=>["rgb","hex","hsl"]},placement:{type:String,default:"bottom-start"},to:Ze.propTo,showAlpha:{type:Boolean,default:!0},showPreview:Boolean,swatches:Array,disabled:{type:Boolean,default:void 0},actions:{type:Array,default:null},internalActions:Array,size:String,renderLabel:Function,onComplete:Function,onConfirm:Function,"onUpdate:show":[Function,Array],onUpdateShow:[Function,Array],"onUpdate:value":[Function,Array],onUpdateValue:[Function,Array]}),ja=z({name:"ColorPicker",props:La,setup(e,{slots:a}){const r=b(null);let t=null;const o=Mt(e),{mergedSizeRef:n,mergedDisabledRef:u}=o,{localeRef:c}=Vt("global"),{mergedClsPrefixRef:p,namespaceRef:m,inlineThemeDisabled:Z}=Rt(e),M=ct("ColorPicker","-color-picker",qa,jt,e,p);Tt(Qe,{themeRef:M,renderLabelRef:Fe(e,"renderLabel"),colorPickerSlots:a});const X=b(e.defaultShow),L=tt(Fe(e,"show"),X);function E(l){const{onUpdateShow:v,"onUpdate:show":w}=e;v&&De(v,l),w&&De(w,l),X.value=l}const{defaultValue:k}=e,$e=b(k===void 0?xa(e.modes,e.showAlpha):k),C=tt(Fe(e,"value"),$e),W=b([C.value]),H=b(0),ge=F(()=>Ae(C.value)),{modes:Pe}=e,T=b(Ae(C.value)||Pe[0]||"rgb");function ye(){const{modes:l}=e,{value:v}=T,w=l.findIndex(y=>y===v);~w?T.value=l[(w+1)%l.length]:T.value="rgb"}let V,$,Y,j,G,O,K,R;const ie=F(()=>{const{value:l}=C;if(!l)return null;switch(ge.value){case"hsv":return pe(l);case"hsl":return[V,$,Y,R]=ke(l),[...vt(V,$,Y),R];case"rgb":case"hex":return[G,O,K,R]=ee(l),[...We(G,O,K),R]}}),J=F(()=>{const{value:l}=C;if(!l)return null;switch(ge.value){case"rgb":case"hex":return ee(l);case"hsv":return[V,$,j,R]=pe(l),[...oe(V,$,j),R];case"hsl":return[V,$,Y,R]=ke(l),[...Je(V,$,Y),R]}}),Ce=F(()=>{const{value:l}=C;if(!l)return null;switch(ge.value){case"hsl":return ke(l);case"hsv":return[V,$,j,R]=pe(l),[...Re(V,$,j),R];case"rgb":case"hex":return[G,O,K,R]=ee(l),[...Ye(G,O,K),R]}}),ze=F(()=>{switch(T.value){case"rgb":case"hex":return J.value;case"hsv":return ie.value;case"hsl":return Ce.value}}),s=b(0),d=b(1),D=b([0,0]);function re(l,v){const{value:w}=ie,y=s.value,_=w?w[3]:1;D.value=[l,v];const{showAlpha:x}=e;switch(T.value){case"hsv":S((x?he:Ke)([y,l,v,_]),"cursor");break;case"hsl":S((x?le:je)([...Re(y,l,v),_]),"cursor");break;case"rgb":S((x?ae:Ge)([...oe(y,l,v),_]),"cursor");break;case"hex":S((x?ne:_e)([...oe(y,l,v),_]),"cursor");break}}function U(l){s.value=l;const{value:v}=ie;if(!v)return;const[,w,y,_]=v,{showAlpha:x}=e;switch(T.value){case"hsv":S((x?he:Ke)([l,w,y,_]),"cursor");break;case"rgb":S((x?ae:Ge)([...oe(l,w,y),_]),"cursor");break;case"hex":S((x?ne:_e)([...oe(l,w,y),_]),"cursor");break;case"hsl":S((x?le:je)([...Re(l,w,y),_]),"cursor");break}}function Ee(l){switch(T.value){case"hsv":[V,$,j]=ie.value,S(he([V,$,j,l]),"cursor");break;case"rgb":[G,O,K]=J.value,S(ae([G,O,K,l]),"cursor");break;case"hex":[G,O,K]=J.value,S(ne([G,O,K,l]),"cursor");break;case"hsl":[V,$,Y]=Ce.value,S(le([V,$,Y,l]),"cursor");break}d.value=l}function S(l,v){v==="cursor"?t=l:t=null;const{nTriggerFormChange:w,nTriggerFormInput:y}=o,{onUpdateValue:_,"onUpdate:value":x}=e;_&&De(_,l),x&&De(x,l),w(),y(),$e.value=l}function bt(l){S(l,"input"),Gt(fe)}function fe(l=!0){const{value:v}=C;if(v){const{nTriggerFormChange:w,nTriggerFormInput:y}=o,{onComplete:_}=e;_&&_(v);const{value:x}=W,{value:N}=H;l&&(x.splice(N+1,x.length,v),H.value=N+1),w(),y()}}function kt(){const{value:l}=H;l-1<0||(S(W.value[l-1],"input"),fe(!1),H.value=l-1)}function xt(){const{value:l}=H;l<0||l+1>=W.value.length||(S(W.value[l+1],"input"),fe(!1),H.value=l+1)}function wt(){S(null,"input"),E(!1)}function yt(){const{value:l}=C,{onConfirm:v}=e;v&&v(l),E(!1)}const Ct=F(()=>H.value>=1),St=F(()=>{const{value:l}=W;return l.length>1&&H.value<l.length-1});Nt(L,l=>{l||(W.value=[C.value],H.value=0)}),ut(()=>{if(!(t&&t===C.value)){const{value:l}=ie;l&&(s.value=l[0],d.value=l[3],D.value=[l[1],l[2]])}t=null});const Be=F(()=>{const{value:l}=n,{common:{cubicBezierEaseInOut:v},self:{textColor:w,color:y,panelFontSize:_,boxShadow:x,border:N,borderRadius:I,dividerColor:se,[at("height",l)]:Ut,[at("fontSize",l)]:At}}=M.value;return{"--n-bezier":v,"--n-text-color":w,"--n-color":y,"--n-panel-font-size":_,"--n-font-size":At,"--n-box-shadow":x,"--n-border":N,"--n-border-radius":I,"--n-height":Ut,"--n-divider-color":se}}),Q=Z?Pt("color-picker",F(()=>n.value[0]),Be,e):void 0;function _t(){var l;const{value:v}=J,{value:w}=s,{internalActions:y,modes:_,actions:x}=e,{value:N}=M,{value:I}=p;return i("div",{class:[`${I}-color-picker-panel`,Q==null?void 0:Q.themeClass.value],onDragstart:se=>{se.preventDefault()},style:Z?void 0:Be.value},i("div",{class:`${I}-color-picker-control`},i(Ia,{clsPrefix:I,rgba:v,displayedHue:w,displayedSv:D.value,onUpdateSV:re,onComplete:fe}),i("div",{class:`${I}-color-picker-preview`},i("div",{class:`${I}-color-picker-preview__sliders`},i(Aa,{clsPrefix:I,hue:w,onUpdateHue:U,onComplete:fe}),e.showAlpha?i(Da,{clsPrefix:I,rgba:v,alpha:d.value,onUpdateAlpha:Ee,onComplete:fe}):null),e.showPreview?i(Oa,{clsPrefix:I,mode:T.value,color:J.value&&_e(J.value),onUpdateColor:se=>{S(se,"input")}}):null),i(za,{clsPrefix:I,showAlpha:e.showAlpha,mode:T.value,modes:_,onUpdateMode:ye,value:C.value,valueArr:ze.value,onUpdateValue:bt}),((l=e.swatches)===null||l===void 0?void 0:l.length)&&i(Ha,{clsPrefix:I,mode:T.value,swatches:e.swatches,onUpdateColor:se=>{S(se,"input")}})),x!=null&&x.length?i("div",{class:`${I}-color-picker-action`},x.includes("confirm")&&i(te,{size:"small",onClick:yt,theme:N.peers.Button,themeOverrides:N.peerOverrides.Button},{default:()=>c.value.confirm}),x.includes("clear")&&i(te,{size:"small",onClick:wt,disabled:!C.value,theme:N.peers.Button,themeOverrides:N.peerOverrides.Button},{default:()=>c.value.clear})):null,a.action?i("div",{class:`${I}-color-picker-action`},{default:a.action}):y?i("div",{class:`${I}-color-picker-action`},y.includes("undo")&&i(te,{size:"small",onClick:kt,disabled:!Ct.value,theme:N.peers.Button,themeOverrides:N.peerOverrides.Button},{default:()=>c.value.undo}),y.includes("redo")&&i(te,{size:"small",onClick:xt,disabled:!St.value,theme:N.peers.Button,themeOverrides:N.peerOverrides.Button},{default:()=>c.value.redo})):null)}return{mergedClsPrefix:p,namespace:m,selfRef:r,hsla:Ce,rgba:J,mergedShow:L,mergedDisabled:u,isMounted:zt(),adjustedTo:Ze(e),mergedValue:C,handleTriggerClick(){E(!0)},handleClickOutside(l){var v;!((v=r.value)===null||v===void 0)&&v.contains(Et(l))||E(!1)},renderPanel:_t,cssVars:Z?void 0:Be,themeClass:Q==null?void 0:Q.themeClass,onRender:Q==null?void 0:Q.onRender}},render(){const{$slots:e,mergedClsPrefix:a,onRender:r}=this;return r==null||r(),i("div",{class:[this.themeClass,`${a}-color-picker`],ref:"selfRef",style:this.cssVars},i(Bt,null,{default:()=>[i(Ft,null,{default:()=>i(Ea,{clsPrefix:a,value:this.mergedValue,hsla:this.hsla,disabled:this.mergedDisabled,onClick:this.handleTriggerClick},{label:e.label})}),i(Ht,{placement:this.placement,show:this.mergedShow,containerClass:this.namespace,teleportDisabled:this.adjustedTo===Ze.tdkey,to:this.adjustedTo},{default:()=>i(Ot,{name:"fade-in-scale-up-transition",appear:this.isMounted},{default:()=>this.mergedShow?qt(this.renderPanel(),[[Lt,this.handleClickOutside,void 0,{capture:!0}]]):null})})]}))}}),Ga={xmlns:"http://www.w3.org/2000/svg","xmlns:xlink":"http://www.w3.org/1999/xlink",viewBox:"0 0 512 512"},Ka=dt('<rect x="64" y="64" width="80" height="80" rx="40" ry="40" fill="none" stroke="currentColor" stroke-miterlimit="10" stroke-width="32"></rect><rect x="216" y="64" width="80" height="80" rx="40" ry="40" fill="none" stroke="currentColor" stroke-miterlimit="10" stroke-width="32"></rect><rect x="368" y="64" width="80" height="80" rx="40" ry="40" fill="none" stroke="currentColor" stroke-miterlimit="10" stroke-width="32"></rect><rect x="64" y="216" width="80" height="80" rx="40" ry="40" fill="none" stroke="currentColor" stroke-miterlimit="10" stroke-width="32"></rect><rect x="216" y="216" width="80" height="80" rx="40" ry="40" fill="none" stroke="currentColor" stroke-miterlimit="10" stroke-width="32"></rect><rect x="368" y="216" width="80" height="80" rx="40" ry="40" fill="none" stroke="currentColor" stroke-miterlimit="10" stroke-width="32"></rect><rect x="64" y="368" width="80" height="80" rx="40" ry="40" fill="none" stroke="currentColor" stroke-miterlimit="10" stroke-width="32"></rect><rect x="216" y="368" width="80" height="80" rx="40" ry="40" fill="none" stroke="currentColor" stroke-miterlimit="10" stroke-width="32"></rect><rect x="368" y="368" width="80" height="80" rx="40" ry="40" fill="none" stroke="currentColor" stroke-miterlimit="10" stroke-width="32"></rect>',9),Za=[Ka],Xa=z({name:"AppsOutline",render:function(a,r){return P(),q("svg",Ga,Za)}}),Wa={xmlns:"http://www.w3.org/2000/svg","xmlns:xlink":"http://www.w3.org/1999/xlink",viewBox:"0 0 512 512"},Ya=dt('<path fill="none" stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="32" d="M160 144h288"></path><path fill="none" stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="32" d="M160 256h288"></path><path fill="none" stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="32" d="M160 368h288"></path><circle cx="80" cy="144" r="16" fill="none" stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="32"></circle><circle cx="80" cy="256" r="16" fill="none" stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="32"></circle><circle cx="80" cy="368" r="16" fill="none" stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="32"></circle>',6),Ja=[Ya],Qa=z({name:"ListOutline",render:function(a,r){return P(),q("svg",Wa,Ja)}}),er={style:{"margin-right":"-10px"}},tr={class:"description"},ar={key:0},rr={key:0},or=z({__name:"TagListItem",props:{tag:{},isChecked:{type:Boolean}},emits:["onEditTag","onDeleteTag","onChecked","onUnChecked"],setup(e,{emit:a}){const r=e,t=Ne("globalVars"),o=a,n=p=>{o("onEditTag",p)},u=p=>{o("onDeleteTag",p)},c=p=>{o(p?"onChecked":"onUnChecked",r.tag)};return(p,m)=>(P(),ht(h(pa),null,{default:f(()=>[g(h(ha),{class:"animate__animated animate__fadeIn"},{avatar:f(()=>[Te("div",er,[g(h(ca),{checked:p.isChecked,style:{"margin-left":"-4px","margin-right":"12px"},onUpdateChecked:c},null,8,["checked"])])]),header:f(()=>[g(h(ma),{class:"none-select pointer",value:p.tag.postCount??0,type:"info"},{default:f(()=>[g(gt,{size:"medium",tag:p.tag},null,8,["tag"])]),_:1},8,["value"])]),description:f(()=>[Te("div",tr,[g(h(ua),{depth:"3"},{default:f(()=>[Kt(pt(p.tag.slug),1)]),_:1})])]),"header-extra":f(()=>[g(h(da),{size:"small"},{default:f(()=>[g(h(te),{type:"default",tertiary:"",onClick:m[0]||(m[0]=Z=>n(p.tag))},{icon:f(()=>[g(h(Xe),null,{default:f(()=>[g(h(Zt))]),_:1})]),default:f(()=>[h(t).isSmallWindow?Ue("",!0):(P(),q("span",ar,"设置"))]),_:1}),g(h(te),{type:"error",tertiary:"",onClick:m[1]||(m[1]=Z=>u(p.tag))},{icon:f(()=>[g(h(Xe),null,{default:f(()=>[g(h(ft))]),_:1})]),default:f(()=>[h(t).isSmallWindow?Ue("",!0):(P(),q("span",rr,"删除"))]),_:1})]),_:1})]),_:1})]),_:1}))}}),nr={class:"container"},lr={key:0},ir={key:0,style:{padding:"10px"}},sr=["onClick"],ur={key:1},kr=z({__name:"TagView",setup(e){const a=Ne("globalVars"),r=b(1),t=b(null),o=b(1),n=b(10),u=b(0),c=b(0),p=b(-1),m=b(Array()),Z=[{label:"查看文章",key:"viewPost",icon:qe(oa)},{type:"divider"},{label:"编辑标签",key:"edit",icon:qe(ka)},{label:"删除标签",key:"delete",icon:qe(ft)}],M=b(ue.ADD),X=b(null),L=b(!1),E=b(!1),k=Xt({tagId:-1,displayName:"",slug:"",color:""}),$e=Jt();Wt(()=>{r.value=Number(localStorage.getItem(Ie.TAG_MODE)??1),n.value=Number(localStorage.getItem(Ie.TAG_PAGE_SIZE)??10);let s=Number($e.query.tagId);isNaN(s)||na(Number(s)).then(d=>{ye(d.data)}).catch(d=>me(d)),C()});const C=()=>{window.$loadingBar.start(),r.value===1?it().then(s=>{var d;t.value=s.data.data,u.value=((d=t.value)==null?void 0:d.length)??0,window.$loadingBar.finish()}).catch(s=>{me(s),window.$loadingBar.error()}):W()},W=()=>{it(o.value,n.value).then(s=>{var D;let d=s.data;if(((D=d.data)==null?void 0:D.length)===0&&d.totalData!==0){o.value=1,W();return}t.value=d.data,u.value=d.totalData,c.value=d.totalPages,window.$loadingBar.finish()}).catch(s=>{me(s),window.$loadingBar.error()})},H=s=>{var D;let d=(D=t.value)==null?void 0:D[p.value];switch(s){case"delete":ge(d);break;case"edit":ye(d);break}},ge=s=>{rt("确定要删除标签 ["+(s==null?void 0:s.displayName)+"] 吗？此操作不可逆。",()=>{T([s.tagId])})},Pe=()=>{let s=Array();m.value.forEach(d=>{s.push(d)}),rt(`确定要删除选择的 ${s.length} 个标签吗？`,()=>{T(s)})},T=s=>{sa(s).then(()=>{He("删除成功"),m.value=m.value.filter(d=>!s.includes(d)),C()}).catch(d=>{me(d)})},ye=s=>{j(),k.tagId=s.tagId,k.displayName=s.displayName,k.slug=s.slug,k.color=s.color??"",M.value=ue.EDIT,L.value=!0},V=()=>{j(),M.value=ue.ADD,L.value=!0},$=()=>{var s;return(s=X.value)==null||s.validate(d=>{if(!d){E.value=!0;let D={tagId:k.tagId,displayName:k.displayName,slug:encodeURIComponent(k.slug),color:k.color};M.value===ue.ADD?la(D).then(()=>{He("添加成功"),Y()}).catch(re=>{me(re),E.value=!1}):ia(D).then(()=>{He("修改成功"),Y()}).catch(re=>{me(re),E.value=!1})}}).catch(()=>{}),!1},Y=()=>{E.value=!1,L.value=!1,j(),C()},j=()=>{k.tagId=-1,k.displayName="",k.slug="",k.color=""},G=s=>{k.displayName=s,M.value===ue.ADD&&(k.slug=ra(s))},O=s=>{r.value=s,C(),localStorage.setItem(Ie.TAG_MODE,s.toString())},K=s=>{o.value=s,C()},R=s=>{n.value=s,localStorage.setItem(Ie.TAG_PAGE_SIZE,s.toString()),C()},ie=s=>{m.value.push(s.tagId)},J=s=>{m.value=m.value.filter(d=>d!==s.tagId)},Ce=()=>{var s;m.value=[],(s=t.value)==null||s.forEach(d=>{m.value.push(d.tagId)})},ze=()=>{m.value=[]};return(s,d)=>{var D,re;return P(),q("div",nr,[g(h(ea),{show:L.value,"onUpdate:show":d[3]||(d[3]=U=>L.value=U),preset:"dialog",title:M.value==h(ue).ADD?"添加标签":"标签设置","positive-text":M.value==h(ue).ADD?"添加":"保存","negative-text":"取消",loading:E.value,onPositiveClick:$,onKeydown:Yt($,["enter"])},{default:f(()=>[g(h(Qt),{ref_key:"addEditDialogRef",ref:X,class:"dialog-form",model:k},{default:f(()=>[g(h(Oe),{label:"标签名",path:"displayName",rule:{required:!0,message:"请输入标签名"}},{default:f(()=>[g(h(Le),{"default-value":k.displayName,"onUpdate:defaultValue":d[0]||(d[0]=U=>k.displayName=U),placeholder:"标签名",onUpdateValue:G,maxlength:"50"},null,8,["default-value"])]),_:1}),g(h(Oe),{label:"别名",path:"slug",rule:{required:!0,message:"请输入标签别名"}},{default:f(()=>[g(h(Le),{value:k.slug,"onUpdate:value":d[1]||(d[1]=U=>k.slug=U),placeholder:"标签别名",maxlength:"50"},null,8,["value"])]),_:1}),g(h(Oe),{label:"颜色",path:"color"},{default:f(()=>[g(h(ja),{value:k.color,"onUpdate:value":d[2]||(d[2]=U=>k.color=U),actions:["clear"],modes:["hex"],"show-alpha":!1},null,8,["value"])]),_:1})]),_:1},8,["model"])]),_:1},8,["show","title","positive-text","loading"]),g(ga,{"data-count":u.value,"show-empty-status":t.value!==null&&t.value.length===0,"current-page":o.value,"page-size":n.value,"page-count":c.value,"current-page-item-count":((D=t.value)==null?void 0:D.length)??0,"show-pagination":r.value==0,"item-string":"标签",onOnPageUpdate:K,onOnPageSizeUpdate:R,"show-checkbox":"",onOnChecked:Ce,onOnCheckboxCancel:ze,"is-checked":m.value.length===((re=t.value)==null?void 0:re.length)&&t.value.length!==0,"show-delete-button":m.value.length>0,onOnDeleteButtonClick:Pe},{"header-extra":f(()=>[g(h(te),{type:"primary",onClick:V},{icon:f(()=>[g(h(Xe),null,{default:f(()=>[g(h(fa))]),_:1})]),default:f(()=>[h(a).isSmallWindow?Ue("",!0):(P(),q("span",lr,"添加标签"))]),_:1})]),content:f(()=>[r.value===1?(P(),q("div",ir,[g(h(ot),null,{default:f(()=>[(P(!0),q(nt,null,lt(t.value,(U,Ee)=>(P(),q("div",{onClick:S=>p.value=Ee},[g(h(ta),{trigger:"hover","keep-alive-on-hover":!1},{trigger:f(()=>[Te("div",null,[g(h(aa),{trigger:"click",options:Z,"show-arrow":"",onSelect:H},{default:f(()=>[g(gt,{size:"medium",tag:U,pointer:""},null,8,["tag"])]),_:2},1024)])]),default:f(()=>[Te("span",null,pt(U.slug),1)]),_:2},1024)],8,sr))),256))]),_:1})])):Ue("",!0),r.value===0?(P(),q("div",ur,[g(h(va),{hoverable:""},{default:f(()=>[(P(!0),q(nt,null,lt(t.value,U=>(P(),ht(or,{tag:U,"is-checked":m.value.includes(U.tagId),onOnDeleteTag:ge,onOnEditTag:ye,onOnChecked:ie,onOnUnChecked:J},null,8,["tag","is-checked"]))),256))]),_:1})])):Ue("",!0)]),"action-left":f(()=>[g(h(ot),null,{default:f(()=>[g(h(te),{class:"btn-switch-mode",circle:"",size:"small",secondary:r.value===1,onClick:d[4]||(d[4]=U=>O(1))},{icon:f(()=>[g(h(Xa))]),_:1},8,["secondary"]),g(h(te),{class:"btn-switch-mode",circle:"",size:"small",secondary:r.value===0,onClick:d[5]||(d[5]=U=>O(0))},{icon:f(()=>[g(h(Qa))]),_:1},8,["secondary"])]),_:1})]),_:1},8,["data-count","show-empty-status","current-page","page-size","page-count","current-page-item-count","show-pagination","is-checked","show-delete-button"])])}}});export{kr as default};
