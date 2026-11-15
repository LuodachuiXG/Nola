import{aL as le,aM as te,aN as ie,aO as pe,aP as re,aQ as xe,aR as ge,d as B,f as b,a9 as s,aS as we,aT as ye,ag as j,aU as Rt,ac as ct,aV as dt,q as qe,aW as Ie,aX as je,aY as Ke,aZ as Ge,a4 as Mt,a_ as Vt,a$ as me,b0 as I,b1 as Pt,b2 as q,b3 as tt,b4 as Nt,b5 as zt,b6 as Bt,b7 as ht,al as Et,am as Oe,b8 as at,G as Ot,b9 as Ft,ba as Lt,bb as Ze,bc as Ht,bd as qt,be as jt,bf as Kt,bg as Gt,bh as Zt,bi as Xt,bj as Wt,bk as Re,bl as rt,B as ae,ah as Yt,o as z,c as G,E as pt,F as gt,m as ft,w as f,a as p,C as ze,u as h,H as Jt,K as Qt,b as ea,J as vt,L as Xe,W as ta,n as Ue,Y as mt,Z as aa,$ as ra,r as bt,R as kt,bm as ce,g as na,h as oa,j as be,a0 as Me,t as la,a1 as ia,a2 as sa,aa as nt,s as Fe,y as ua,x as Le,a6 as ca,a7 as da,S as ot,T as lt,U as it,I as ha,ay as pa,a8 as ga,aB as He,aI as fa,bn as va,V as ma}from"./index-BUZClAFS.js";import{_ as xt,b as ba,t as st,a as ka,u as xa,d as wa}from"./tagApi-D4Wf3ArX.js";import{N as ya}from"./Badge-cnU1liBZ.js";import{N as Ca}from"./ButtonGroup-Pf0zCCyZ.js";function wt(e,a,r){a/=100,r/=100;const t=a*Math.min(r,1-r)+r;return[e,t?(2-2*r/t)*100:0,t*100]}function Ne(e,a,r){a/=100,r/=100;const t=r-r*a/2,n=Math.min(t,1-t);return[e,n?(r-t)/n*100:0,t*100]}function oe(e,a,r){a/=100,r/=100;let t=(n,o=(n+e/60)%6)=>r-r*a*Math.max(Math.min(o,4-o,1),0);return[t(5)*255,t(3)*255,t(1)*255]}function We(e,a,r){e/=255,a/=255,r/=255;let t=Math.max(e,a,r),n=t-Math.min(e,a,r),o=n&&(t==e?(a-r)/n:t==a?2+(r-e)/n:4+(e-a)/n);return[60*(o<0?o+6:o),t&&n/t*100,t*100]}function Ye(e,a,r){e/=255,a/=255,r/=255;let t=Math.max(e,a,r),n=t-Math.min(e,a,r),o=1-Math.abs(t+t-n-1),u=n&&(t==e?(a-r)/n:t==a?2+(r-e)/n:4+(e-a)/n);return[60*(u<0?u+6:u),o?n/o*100:0,(t+t-n)*50]}function Je(e,a,r){a/=100,r/=100;let t=a*Math.min(r,1-r),n=(o,u=(o+e/30)%12)=>r-t*Math.max(Math.min(u-3,9-u,1),-1);return[n(0)*255,n(8)*255,n(4)*255]}function Sa(e,a){switch(e[0]){case"hex":return a?"#000000FF":"#000000";case"rgb":return a?"rgba(0, 0, 0, 1)":"rgb(0, 0, 0)";case"hsl":return a?"hsla(0, 0%, 0%, 1)":"hsl(0, 0%, 0%)";case"hsv":return a?"hsva(0, 0%, 0%, 1)":"hsv(0, 0%, 0%)"}return"#000000"}function $e(e){return e===null?null:/^ *#/.test(e)?"hex":e.includes("rgb")?"rgb":e.includes("hsl")?"hsl":e.includes("hsv")?"hsv":null}function _a(e){return e=Math.round(e),e>=360?359:e<0?0:e}function Ia(e){return e=Math.round(e*100)/100,e>1?1:e<0?0:e}const Ua={rgb:{hex(e){return le(te(e))},hsl(e){const[a,r,t,n]=te(e);return ie([...Ye(a,r,t),n])},hsv(e){const[a,r,t,n]=te(e);return pe([...We(a,r,t),n])}},hex:{rgb(e){return re(te(e))},hsl(e){const[a,r,t,n]=te(e);return ie([...Ye(a,r,t),n])},hsv(e){const[a,r,t,n]=te(e);return pe([...We(a,r,t),n])}},hsl:{hex(e){const[a,r,t,n]=xe(e);return le([...Je(a,r,t),n])},rgb(e){const[a,r,t,n]=xe(e);return re([...Je(a,r,t),n])},hsv(e){const[a,r,t,n]=xe(e);return pe([...wt(a,r,t),n])}},hsv:{hex(e){const[a,r,t,n]=ge(e);return le([...oe(a,r,t),n])},rgb(e){const[a,r,t,n]=ge(e);return re([...oe(a,r,t),n])},hsl(e){const[a,r,t,n]=ge(e);return ie([...Ne(a,r,t),n])}}};function yt(e,a,r){return r=r||$e(e),r?r===a?e:Ua[r][a](e):null}const ke="12px",$a=12,de="6px",Aa=6,Da="linear-gradient(90deg,red,#ff0 16.66%,#0f0 33.33%,#0ff 50%,#00f 66.66%,#f0f 83.33%,red)",Ta=B({name:"HueSlider",props:{clsPrefix:{type:String,required:!0},hue:{type:Number,required:!0},onUpdateHue:{type:Function,required:!0},onComplete:Function},setup(e){const a=b(null);function r(o){a.value&&(we("mousemove",document,t),we("mouseup",document,n),t(o))}function t(o){const{value:u}=a;if(!u)return;const{width:d,left:m}=u.getBoundingClientRect(),g=_a((o.clientX-m-Aa)/(d-$a)*360);e.onUpdateHue(g)}function n(){var o;ye("mousemove",document,t),ye("mouseup",document,n),(o=e.onComplete)===null||o===void 0||o.call(e)}return{railRef:a,handleMouseDown:r}},render(){const{clsPrefix:e}=this;return s("div",{class:`${e}-color-picker-slider`,style:{height:ke,borderRadius:de}},s("div",{ref:"railRef",style:{boxShadow:"inset 0 0 2px 0 rgba(0, 0, 0, .24)",boxSizing:"border-box",backgroundImage:Da,height:ke,borderRadius:de,position:"relative"},onMousedown:this.handleMouseDown},s("div",{style:{position:"absolute",left:de,right:de,top:0,bottom:0}},s("div",{class:`${e}-color-picker-handle`,style:{left:`calc((${this.hue}%) / 359 * 100 - ${de})`,borderRadius:de,width:ke,height:ke}},s("div",{class:`${e}-color-picker-handle__fill`,style:{backgroundColor:`hsl(${this.hue}, 100%, 50%)`,borderRadius:de,width:ke,height:ke}})))))}}),_e="12px",Ra=12,he="6px",Ma=B({name:"AlphaSlider",props:{clsPrefix:{type:String,required:!0},rgba:{type:Array,default:null},alpha:{type:Number,default:0},onUpdateAlpha:{type:Function,required:!0},onComplete:Function},setup(e){const a=b(null);function r(o){!a.value||!e.rgba||(we("mousemove",document,t),we("mouseup",document,n),t(o))}function t(o){const{value:u}=a;if(!u)return;const{width:d,left:m}=u.getBoundingClientRect(),g=(o.clientX-m)/(d-Ra);e.onUpdateAlpha(Ia(g))}function n(){var o;ye("mousemove",document,t),ye("mouseup",document,n),(o=e.onComplete)===null||o===void 0||o.call(e)}return{railRef:a,railBackgroundImage:j(()=>{const{rgba:o}=e;return o?`linear-gradient(to right, rgba(${o[0]}, ${o[1]}, ${o[2]}, 0) 0%, rgba(${o[0]}, ${o[1]}, ${o[2]}, 1) 100%)`:""}),handleMouseDown:r}},render(){const{clsPrefix:e}=this;return s("div",{class:`${e}-color-picker-slider`,ref:"railRef",style:{height:_e,borderRadius:he},onMousedown:this.handleMouseDown},s("div",{style:{borderRadius:he,position:"absolute",left:0,right:0,top:0,bottom:0,overflow:"hidden"}},s("div",{class:`${e}-color-picker-checkboard`}),s("div",{class:`${e}-color-picker-slider__image`,style:{backgroundImage:this.railBackgroundImage}})),this.rgba&&s("div",{style:{position:"absolute",left:he,right:he,top:0,bottom:0}},s("div",{class:`${e}-color-picker-handle`,style:{left:`calc(${this.alpha*100}% - ${he})`,borderRadius:he,width:_e,height:_e}},s("div",{class:`${e}-color-picker-handle__fill`,style:{backgroundColor:re(this.rgba),borderRadius:he,width:_e,height:_e}}))))}}),Ve="12px",Pe="6px",Va=B({name:"Pallete",props:{clsPrefix:{type:String,required:!0},rgba:{type:Array,default:null},displayedHue:{type:Number,required:!0},displayedSv:{type:Array,required:!0},onUpdateSV:{type:Function,required:!0},onComplete:Function},setup(e){const a=b(null);function r(o){a.value&&(we("mousemove",document,t),we("mouseup",document,n),t(o))}function t(o){const{value:u}=a;if(!u)return;const{width:d,height:m,left:g,bottom:E}=u.getBoundingClientRect(),U=(E-o.clientY)/m,W=(o.clientX-g)/d,Z=100*(W>1?1:W<0?0:W),O=100*(U>1?1:U<0?0:U);e.onUpdateSV(Z,O)}function n(){var o;ye("mousemove",document,t),ye("mouseup",document,n),(o=e.onComplete)===null||o===void 0||o.call(e)}return{palleteRef:a,handleColor:j(()=>{const{rgba:o}=e;return o?`rgb(${o[0]}, ${o[1]}, ${o[2]})`:""}),handleMouseDown:r}},render(){const{clsPrefix:e}=this;return s("div",{class:`${e}-color-picker-pallete`,onMousedown:this.handleMouseDown,ref:"palleteRef"},s("div",{class:`${e}-color-picker-pallete__layer`,style:{backgroundImage:`linear-gradient(90deg, white, hsl(${this.displayedHue}, 100%, 50%))`}}),s("div",{class:`${e}-color-picker-pallete__layer ${e}-color-picker-pallete__layer--shadowed`,style:{backgroundImage:"linear-gradient(180deg, rgba(0, 0, 0, 0%), rgba(0, 0, 0, 100%))"}}),this.rgba&&s("div",{class:`${e}-color-picker-handle`,style:{width:Ve,height:Ve,borderRadius:Pe,left:`calc(${this.displayedSv[0]}% - ${Pe})`,bottom:`calc(${this.displayedSv[1]}% - ${Pe})`}},s("div",{class:`${e}-color-picker-handle__fill`,style:{backgroundColor:this.handleColor,borderRadius:Pe,width:Ve,height:Ve}})))}}),Qe=Rt("n-color-picker");function Pa(e){return/^\d{1,3}\.?\d*$/.test(e.trim())?Math.max(0,Math.min(parseInt(e),255)):!1}function Na(e){return/^\d{1,3}\.?\d*$/.test(e.trim())?Math.max(0,Math.min(parseInt(e),360)):!1}function za(e){return/^\d{1,3}\.?\d*$/.test(e.trim())?Math.max(0,Math.min(parseInt(e),100)):!1}function Ba(e){const a=e.trim();return/^#[0-9a-fA-F]+$/.test(a)?[4,5,7,9].includes(a.length):!1}function Ea(e){return/^\d{1,3}\.?\d*%$/.test(e.trim())?Math.max(0,Math.min(parseInt(e)/100,100)):!1}const Oa={paddingSmall:"0 4px"},ut=B({name:"ColorInputUnit",props:{label:{type:String,required:!0},value:{type:[Number,String],default:null},showAlpha:Boolean,onUpdateValue:{type:Function,required:!0}},setup(e){const a=b(""),{themeRef:r}=ct(Qe,null);dt(()=>{a.value=t()});function t(){const{value:u}=e;if(u===null)return"";const{label:d}=e;return d==="HEX"?u:d==="A"?`${Math.floor(u*100)}%`:String(Math.floor(u))}function n(u){a.value=u}function o(u){let d,m;switch(e.label){case"HEX":m=Ba(u),m&&e.onUpdateValue(u),a.value=t();break;case"H":d=Na(u),d===!1?a.value=t():e.onUpdateValue(d);break;case"S":case"L":case"V":d=za(u),d===!1?a.value=t():e.onUpdateValue(d);break;case"A":d=Ea(u),d===!1?a.value=t():e.onUpdateValue(d);break;case"R":case"G":case"B":d=Pa(u),d===!1?a.value=t():e.onUpdateValue(d);break}}return{mergedTheme:r,inputValue:a,handleInputChange:o,handleInputUpdateValue:n}},render(){const{mergedTheme:e}=this;return s(qe,{size:"small",placeholder:this.label,theme:e.peers.Input,themeOverrides:e.peerOverrides.Input,builtinThemeOverrides:Oa,value:this.inputValue,onUpdateValue:this.handleInputUpdateValue,onChange:this.handleInputChange,style:this.label==="A"?"flex-grow: 1.25;":""})}}),Fa=B({name:"ColorInput",props:{clsPrefix:{type:String,required:!0},mode:{type:String,required:!0},modes:{type:Array,required:!0},showAlpha:{type:Boolean,required:!0},value:{type:String,default:null},valueArr:{type:Array,default:null},onUpdateValue:{type:Function,required:!0},onUpdateMode:{type:Function,required:!0}},setup(e){return{handleUnitUpdateValue(a,r){const{showAlpha:t}=e;if(e.mode==="hex"){e.onUpdateValue((t?le:Ie)(r));return}let n;switch(e.valueArr===null?n=[0,0,0,0]:n=Array.from(e.valueArr),e.mode){case"hsv":n[a]=r,e.onUpdateValue((t?pe:Ge)(n));break;case"rgb":n[a]=r,e.onUpdateValue((t?re:Ke)(n));break;case"hsl":n[a]=r,e.onUpdateValue((t?ie:je)(n));break}}}},render(){const{clsPrefix:e,modes:a}=this;return s("div",{class:`${e}-color-picker-input`},s("div",{class:`${e}-color-picker-input__mode`,onClick:this.onUpdateMode,style:{cursor:a.length===1?"":"pointer"}},this.mode.toUpperCase()+(this.showAlpha?"A":"")),s(Mt,null,{default:()=>{const{mode:r,valueArr:t,showAlpha:n}=this;if(r==="hex"){let o=null;try{o=t===null?null:(n?le:Ie)(t)}catch{}return s(ut,{label:"HEX",showAlpha:n,value:o,onUpdateValue:u=>{this.handleUnitUpdateValue(0,u)}})}return(r+(n?"a":"")).split("").map((o,u)=>s(ut,{label:o.toUpperCase(),value:t===null?null:t[u],onUpdateValue:d=>{this.handleUnitUpdateValue(u,d)}}))}}))}}),La=B({name:"ColorPickerTrigger",props:{clsPrefix:{type:String,required:!0},value:{type:String,default:null},hsla:{type:Array,default:null},disabled:Boolean,onClick:Function},setup(e){const{colorPickerSlots:a,renderLabelRef:r}=ct(Qe,null);return()=>{const{hsla:t,value:n,clsPrefix:o,onClick:u,disabled:d}=e,m=a.label||r.value;return s("div",{class:[`${o}-color-picker-trigger`,d&&`${o}-color-picker-trigger--disabled`],onClick:d?void 0:u},s("div",{class:`${o}-color-picker-trigger__fill`},s("div",{class:`${o}-color-picker-checkboard`}),s("div",{style:{position:"absolute",left:0,right:0,top:0,bottom:0,backgroundColor:t?ie(t):""}}),n&&t?s("div",{class:`${o}-color-picker-trigger__value`,style:{color:t[2]>50||t[3]<.5?"black":"white"}},m?m(n):n):null))}}});function Ha(e,a){if(a==="hsv"){const[r,t,n,o]=ge(e);return re([...oe(r,t,n),o])}return e}function qa(e){const a=document.createElement("canvas").getContext("2d");return a?(a.fillStyle=e,a.fillStyle):"#000000"}const ja=B({name:"ColorPickerSwatches",props:{clsPrefix:{type:String,required:!0},mode:{type:String,required:!0},swatches:{type:Array,required:!0},onUpdateColor:{type:Function,required:!0}},setup(e){const a=j(()=>e.swatches.map(o=>{const u=$e(o);return{value:o,mode:u,legalValue:Ha(o,u)}}));function r(o){const{mode:u}=e;let{value:d,mode:m}=o;return m||(m="hex",/^[a-zA-Z]+$/.test(d)?d=qa(d):(Vt("color-picker",`color ${d} in swatches is invalid.`),d="#000000")),m===u?d:yt(d,u,m)}function t(o){e.onUpdateColor(r(o))}function n(o,u){o.key==="Enter"&&t(u)}return{parsedSwatchesRef:a,handleSwatchSelect:t,handleSwatchKeyDown:n}},render(){const{clsPrefix:e}=this;return s("div",{class:`${e}-color-picker-swatches`},this.parsedSwatchesRef.map(a=>s("div",{class:`${e}-color-picker-swatch`,tabindex:0,onClick:()=>{this.handleSwatchSelect(a)},onKeydown:r=>{this.handleSwatchKeyDown(r,a)}},s("div",{class:`${e}-color-picker-swatch__fill`,style:{background:a.legalValue}}))))}}),Ka=B({name:"ColorPreview",props:{clsPrefix:{type:String,required:!0},mode:{type:String,required:!0},color:{type:String,default:null,validator:e=>{const a=$e(e);return!!(!e||a&&a!=="hsv")}},onUpdateColor:{type:Function,required:!0}},setup(e){function a(r){var t;const n=r.target.value;(t=e.onUpdateColor)===null||t===void 0||t.call(e,yt(n.toUpperCase(),e.mode,"hex")),r.stopPropagation()}return{handleChange:a}},render(){const{clsPrefix:e}=this;return s("div",{class:`${e}-color-picker-preview__preview`},s("span",{class:`${e}-color-picker-preview__fill`,style:{background:this.color||"#000000"}}),s("input",{class:`${e}-color-picker-preview__input`,type:"color",value:this.color,onChange:this.handleChange}))}}),Ga=me([I("color-picker",`
 display: inline-block;
 box-sizing: border-box;
 height: var(--n-height);
 font-size: var(--n-font-size);
 width: 100%;
 position: relative;
 `),I("color-picker-panel",`
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
 `,[Pt(),I("input",`
 text-align: center;
 `)]),I("color-picker-checkboard",`
 background: white; 
 position: absolute;
 left: 0;
 right: 0;
 top: 0;
 bottom: 0;
 `,[me("&::after",`
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
 `)]),I("color-picker-slider",`
 margin-bottom: 8px;
 position: relative;
 box-sizing: border-box;
 `,[q("image",`
 position: absolute;
 left: 0;
 right: 0;
 top: 0;
 bottom: 0;
 `),me("&::after",`
 content: "";
 position: absolute;
 border-radius: inherit;
 left: 0;
 right: 0;
 top: 0;
 bottom: 0;
 box-shadow: inset 0 0 2px 0 rgba(0, 0, 0, .24);
 pointer-events: none;
 `)]),I("color-picker-handle",`
 z-index: 1;
 box-shadow: 0 0 2px 0 rgba(0, 0, 0, .45);
 position: absolute;
 background-color: white;
 overflow: hidden;
 `,[q("fill",`
 box-sizing: border-box;
 border: 2px solid white;
 `)]),I("color-picker-pallete",`
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
 `,[tt("shadowed",`
 box-shadow: inset 0 0 2px 0 rgba(0, 0, 0, .24);
 `)])]),I("color-picker-preview",`
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
 `)]),I("color-picker-input",`
 display: flex;
 align-items: center;
 `,[I("input",`
 flex-grow: 1;
 flex-basis: 0;
 `),q("mode",`
 width: 72px;
 text-align: center;
 `)]),I("color-picker-control",`
 padding: 12px;
 `),I("color-picker-action",`
 display: flex;
 margin-top: -4px;
 border-top: 1px solid var(--n-divider-color);
 padding: 8px 12px;
 justify-content: flex-end;
 `,[I("button","margin-left: 8px;")]),I("color-picker-trigger",`
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
 `),tt("disabled","cursor: not-allowed"),I("color-picker-checkboard",`
 border-radius: var(--n-border-radius);
 `,[me("&::after",`
 --n-block-size: calc((var(--n-height) - 8px) / 3);
 background-size: calc(var(--n-block-size) * 2) calc(var(--n-block-size) * 2);
 background-position: 0 0, 0 var(--n-block-size), var(--n-block-size) calc(-1 * var(--n-block-size)), calc(-1 * var(--n-block-size)) 0px; 
 `)])]),I("color-picker-swatches",`
 display: grid;
 grid-gap: 8px;
 flex-wrap: wrap;
 position: relative;
 grid-template-columns: repeat(auto-fill, 18px);
 margin-top: 10px;
 `,[I("color-picker-swatch",`
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
 `),me("&:focus",`
 outline: none;
 `,[q("fill",[me("&::after",`
 position: absolute;
 top: 0;
 right: 0;
 bottom: 0;
 left: 0;
 background: inherit;
 filter: blur(2px);
 content: "";
 `)])])])])]),Za=Object.assign(Object.assign({},ht.props),{value:String,show:{type:Boolean,default:void 0},defaultShow:Boolean,defaultValue:String,modes:{type:Array,default:()=>["rgb","hex","hsl"]},placement:{type:String,default:"bottom-start"},to:Ze.propTo,showAlpha:{type:Boolean,default:!0},showPreview:Boolean,swatches:Array,disabled:{type:Boolean,default:void 0},actions:{type:Array,default:null},internalActions:Array,size:String,renderLabel:Function,onComplete:Function,onConfirm:Function,"onUpdate:show":[Function,Array],onUpdateShow:[Function,Array],"onUpdate:value":[Function,Array],onUpdateValue:[Function,Array]}),Xa=B({name:"ColorPicker",props:Za,setup(e,{slots:a}){const r=b(null);let t=null;const n=Nt(e),{mergedSizeRef:o,mergedDisabledRef:u}=n,{localeRef:d}=zt("global"),{mergedClsPrefixRef:m,namespaceRef:g,inlineThemeDisabled:E}=Bt(e),U=ht("ColorPicker","-color-picker",Ga,Wt,e,m);Et(Qe,{themeRef:U,renderLabelRef:Oe(e,"renderLabel"),colorPickerSlots:a});const W=b(e.defaultShow),Z=at(Oe(e,"show"),W);function O(l){const{onUpdateShow:v,"onUpdate:show":w}=e;v&&Re(v,l),w&&Re(w,l),W.value=l}const{defaultValue:k}=e,Ae=b(k===void 0?Sa(e.modes,e.showAlpha):k),A=at(Oe(e,"value"),Ae),M=b([A.value]),F=b(0),Ce=j(()=>$e(A.value)),{modes:De}=e,L=b($e(A.value)||De[0]||"rgb");function Te(){const{modes:l}=e,{value:v}=L,w=l.findIndex(y=>y===v);~w?L.value=l[(w+1)%l.length]:L.value="rgb"}let $,T,Y,J,H,X,K,R;const se=j(()=>{const{value:l}=A;if(!l)return null;switch(Ce.value){case"hsv":return ge(l);case"hsl":return[$,T,Y,R]=xe(l),[...wt($,T,Y),R];case"rgb":case"hex":return[H,X,K,R]=te(l),[...We(H,X,K),R]}}),Q=j(()=>{const{value:l}=A;if(!l)return null;switch(Ce.value){case"rgb":case"hex":return te(l);case"hsv":return[$,T,J,R]=ge(l),[...oe($,T,J),R];case"hsl":return[$,T,Y,R]=xe(l),[...Je($,T,Y),R]}}),Se=j(()=>{const{value:l}=A;if(!l)return null;switch(Ce.value){case"hsl":return xe(l);case"hsv":return[$,T,J,R]=ge(l),[...Ne($,T,J),R];case"rgb":case"hex":return[H,X,K,R]=te(l),[...Ye(H,X,K),R]}}),Be=j(()=>{switch(L.value){case"rgb":case"hex":return Q.value;case"hsv":return se.value;case"hsl":return Se.value}}),fe=b(0),i=b(1),c=b([0,0]);function V(l,v){const{value:w}=se,y=fe.value,S=w?w[3]:1;c.value=[l,v];const{showAlpha:x}=e;switch(L.value){case"hsv":C((x?pe:Ge)([y,l,v,S]),"cursor");break;case"hsl":C((x?ie:je)([...Ne(y,l,v),S]),"cursor");break;case"rgb":C((x?re:Ke)([...oe(y,l,v),S]),"cursor");break;case"hex":C((x?le:Ie)([...oe(y,l,v),S]),"cursor");break}}function ne(l){fe.value=l;const{value:v}=se;if(!v)return;const[,w,y,S]=v,{showAlpha:x}=e;switch(L.value){case"hsv":C((x?pe:Ge)([l,w,y,S]),"cursor");break;case"rgb":C((x?re:Ke)([...oe(l,w,y),S]),"cursor");break;case"hex":C((x?le:Ie)([...oe(l,w,y),S]),"cursor");break;case"hsl":C((x?ie:je)([...Ne(l,w,y),S]),"cursor");break}}function _(l){switch(L.value){case"hsv":[$,T,J]=se.value,C(pe([$,T,J,l]),"cursor");break;case"rgb":[H,X,K]=Q.value,C(re([H,X,K,l]),"cursor");break;case"hex":[H,X,K]=Q.value,C(le([H,X,K,l]),"cursor");break;case"hsl":[$,T,Y]=Se.value,C(ie([$,T,Y,l]),"cursor");break}i.value=l}function C(l,v){v==="cursor"?t=l:t=null;const{nTriggerFormChange:w,nTriggerFormInput:y}=n,{onUpdateValue:S,"onUpdate:value":x}=e;S&&Re(S,l),x&&Re(x,l),w(),y(),Ae.value=l}function et(l){C(l,"input"),Yt(ve)}function ve(l=!0){const{value:v}=A;if(v){const{nTriggerFormChange:w,nTriggerFormInput:y}=n,{onComplete:S}=e;S&&S(v);const{value:x}=M,{value:P}=F;l&&(x.splice(P+1,x.length,v),F.value=P+1),w(),y()}}function Ct(){const{value:l}=F;l-1<0||(C(M.value[l-1],"input"),ve(!1),F.value=l-1)}function St(){const{value:l}=F;l<0||l+1>=M.value.length||(C(M.value[l+1],"input"),ve(!1),F.value=l+1)}function _t(){C(null,"input"),O(!1)}function It(){const{value:l}=A,{onConfirm:v}=e;v&&v(l),O(!1)}const Ut=j(()=>F.value>=1),$t=j(()=>{const{value:l}=M;return l.length>1&&F.value<l.length-1});Ot(Z,l=>{l||(M.value=[A.value],F.value=0)}),dt(()=>{if(!(t&&t===A.value)){const{value:l}=se;l&&(fe.value=l[0],i.value=l[3],c.value=[l[1],l[2]])}t=null});const Ee=j(()=>{const{value:l}=o,{common:{cubicBezierEaseInOut:v},self:{textColor:w,color:y,panelFontSize:S,boxShadow:x,border:P,borderRadius:D,dividerColor:ue,[rt("height",l)]:Dt,[rt("fontSize",l)]:Tt}}=U.value;return{"--n-bezier":v,"--n-text-color":w,"--n-color":y,"--n-panel-font-size":S,"--n-font-size":Tt,"--n-box-shadow":x,"--n-border":P,"--n-border-radius":D,"--n-height":Dt,"--n-divider-color":ue}}),ee=E?Ft("color-picker",j(()=>o.value[0]),Ee,e):void 0;function At(){var l;const{value:v}=Q,{value:w}=fe,{internalActions:y,modes:S,actions:x}=e,{value:P}=U,{value:D}=m;return s("div",{class:[`${D}-color-picker-panel`,ee==null?void 0:ee.themeClass.value],onDragstart:ue=>{ue.preventDefault()},style:E?void 0:Ee.value},s("div",{class:`${D}-color-picker-control`},s(Va,{clsPrefix:D,rgba:v,displayedHue:w,displayedSv:c.value,onUpdateSV:V,onComplete:ve}),s("div",{class:`${D}-color-picker-preview`},s("div",{class:`${D}-color-picker-preview__sliders`},s(Ta,{clsPrefix:D,hue:w,onUpdateHue:ne,onComplete:ve}),e.showAlpha?s(Ma,{clsPrefix:D,rgba:v,alpha:i.value,onUpdateAlpha:_,onComplete:ve}):null),e.showPreview?s(Ka,{clsPrefix:D,mode:L.value,color:Q.value&&Ie(Q.value),onUpdateColor:ue=>{C(ue,"input")}}):null),s(Fa,{clsPrefix:D,showAlpha:e.showAlpha,mode:L.value,modes:S,onUpdateMode:Te,value:A.value,valueArr:Be.value,onUpdateValue:et}),((l=e.swatches)===null||l===void 0?void 0:l.length)&&s(ja,{clsPrefix:D,mode:L.value,swatches:e.swatches,onUpdateColor:ue=>{C(ue,"input")}})),x!=null&&x.length?s("div",{class:`${D}-color-picker-action`},x.includes("confirm")&&s(ae,{size:"small",onClick:It,theme:P.peers.Button,themeOverrides:P.peerOverrides.Button},{default:()=>d.value.confirm}),x.includes("clear")&&s(ae,{size:"small",onClick:_t,disabled:!A.value,theme:P.peers.Button,themeOverrides:P.peerOverrides.Button},{default:()=>d.value.clear})):null,a.action?s("div",{class:`${D}-color-picker-action`},{default:a.action}):y?s("div",{class:`${D}-color-picker-action`},y.includes("undo")&&s(ae,{size:"small",onClick:Ct,disabled:!Ut.value,theme:P.peers.Button,themeOverrides:P.peerOverrides.Button},{default:()=>d.value.undo}),y.includes("redo")&&s(ae,{size:"small",onClick:St,disabled:!$t.value,theme:P.peers.Button,themeOverrides:P.peerOverrides.Button},{default:()=>d.value.redo})):null)}return{mergedClsPrefix:m,namespace:g,selfRef:r,hsla:Se,rgba:Q,mergedShow:Z,mergedDisabled:u,isMounted:Lt(),adjustedTo:Ze(e),mergedValue:A,handleTriggerClick(){O(!0)},handleClickOutside(l){var v;!((v=r.value)===null||v===void 0)&&v.contains(Ht(l))||O(!1)},renderPanel:At,cssVars:E?void 0:Ee,themeClass:ee==null?void 0:ee.themeClass,onRender:ee==null?void 0:ee.onRender}},render(){const{$slots:e,mergedClsPrefix:a,onRender:r}=this;return r==null||r(),s("div",{class:[this.themeClass,`${a}-color-picker`],ref:"selfRef",style:this.cssVars},s(qt,null,{default:()=>[s(jt,null,{default:()=>s(La,{clsPrefix:a,value:this.mergedValue,hsla:this.hsla,disabled:this.mergedDisabled,onClick:this.handleTriggerClick},{label:e.label})}),s(Kt,{placement:this.placement,show:this.mergedShow,containerClass:this.namespace,teleportDisabled:this.adjustedTo===Ze.tdkey,to:this.adjustedTo},{default:()=>s(Gt,{name:"fade-in-scale-up-transition",appear:this.isMounted},{default:()=>this.mergedShow?Zt(this.renderPanel(),[[Xt,this.handleClickOutside,void 0,{capture:!0}]]):null})})]}))}}),Wa={xmlns:"http://www.w3.org/2000/svg","xmlns:xlink":"http://www.w3.org/1999/xlink",viewBox:"0 0 512 512"},Ya=pt('<rect x="64" y="64" width="80" height="80" rx="40" ry="40" fill="none" stroke="currentColor" stroke-miterlimit="10" stroke-width="32"></rect><rect x="216" y="64" width="80" height="80" rx="40" ry="40" fill="none" stroke="currentColor" stroke-miterlimit="10" stroke-width="32"></rect><rect x="368" y="64" width="80" height="80" rx="40" ry="40" fill="none" stroke="currentColor" stroke-miterlimit="10" stroke-width="32"></rect><rect x="64" y="216" width="80" height="80" rx="40" ry="40" fill="none" stroke="currentColor" stroke-miterlimit="10" stroke-width="32"></rect><rect x="216" y="216" width="80" height="80" rx="40" ry="40" fill="none" stroke="currentColor" stroke-miterlimit="10" stroke-width="32"></rect><rect x="368" y="216" width="80" height="80" rx="40" ry="40" fill="none" stroke="currentColor" stroke-miterlimit="10" stroke-width="32"></rect><rect x="64" y="368" width="80" height="80" rx="40" ry="40" fill="none" stroke="currentColor" stroke-miterlimit="10" stroke-width="32"></rect><rect x="216" y="368" width="80" height="80" rx="40" ry="40" fill="none" stroke="currentColor" stroke-miterlimit="10" stroke-width="32"></rect><rect x="368" y="368" width="80" height="80" rx="40" ry="40" fill="none" stroke="currentColor" stroke-miterlimit="10" stroke-width="32"></rect>',9),Ja=[Ya],Qa=B({name:"AppsOutline",render:function(a,r){return z(),G("svg",Wa,Ja)}}),er={xmlns:"http://www.w3.org/2000/svg","xmlns:xlink":"http://www.w3.org/1999/xlink",viewBox:"0 0 512 512"},tr=pt('<path fill="none" stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="32" d="M160 144h288"></path><path fill="none" stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="32" d="M160 256h288"></path><path fill="none" stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="32" d="M160 368h288"></path><circle cx="80" cy="144" r="16" fill="none" stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="32"></circle><circle cx="80" cy="256" r="16" fill="none" stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="32"></circle><circle cx="80" cy="368" r="16" fill="none" stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="32"></circle>',6),ar=[tr],rr=B({name:"ListOutline",render:function(a,r){return z(),G("svg",er,ar)}}),nr={style:{"margin-right":"-10px"}},or={class:"description"},lr={key:0},ir={key:0},sr=B({__name:"TagListItem",props:{tag:{},isChecked:{type:Boolean}},emits:["onEditTag","onDeleteTag","onChecked","onUnChecked"],setup(e,{emit:a}){const r=e,t=gt(),n=a,o=g=>{n("onEditTag",g)},u=g=>{n("onDeleteTag",g)},d=g=>{n(g?"onChecked":"onUnChecked",r.tag)},m=()=>{bt.push({name:kt.POST.name,query:{tagId:r.tag.tagId}})};return(g,E)=>(z(),ft(h(ra),null,{default:f(()=>[p(h(aa),{class:"animate__animated animate__fadeIn"},{avatar:f(()=>[ze("div",nr,[p(h(Jt),{checked:g.isChecked,style:{"margin-left":"-4px","margin-right":"12px"},onUpdateChecked:d},null,8,["checked"])])]),header:f(()=>[p(h(ya),{class:"none-select pointer",value:g.tag.postCount??0,type:"info",onClick:m},{default:f(()=>[p(xt,{size:"medium",tag:g.tag},null,8,["tag"])]),_:1},8,["value"])]),description:f(()=>[ze("div",or,[p(h(Qt),{depth:"3"},{default:f(()=>[ea(vt(g.tag.slug),1)]),_:1})])]),"header-extra":f(()=>[p(h(Ca),{size:"small"},{default:f(()=>[p(h(ae),{type:"default",tertiary:"",onClick:E[0]||(E[0]=U=>o(g.tag))},{icon:f(()=>[p(h(Xe),null,{default:f(()=>[p(h(ta))]),_:1})]),default:f(()=>[h(t).isSmallWindow?Ue("",!0):(z(),G("span",lr,"设置"))]),_:1}),p(h(ae),{type:"error",tertiary:"",onClick:E[1]||(E[1]=U=>u(g.tag))},{icon:f(()=>[p(h(Xe),null,{default:f(()=>[p(h(mt))]),_:1})]),default:f(()=>[h(t).isSmallWindow?Ue("",!0):(z(),G("span",ir,"删除"))]),_:1})]),_:1})]),_:1})]),_:1}))}});var N=(e=>(e[e.LIST=0]="LIST",e[e.BLOCK=1]="BLOCK",e))(N||{});const ur={class:"container"},cr={key:0},dr={key:0,style:{padding:"10px"}},hr=["onClick"],pr={key:1},br=B({__name:"TagView",setup(e){const a=gt(),r=b(N.BLOCK),t=b(null),n=b(1),o=b(10),u=b(0),d=b(0),m=b(-1),g=b(Array()),E=[{label:"查看文章",key:"viewPost",icon:He(va)},{type:"divider"},{label:"编辑标签",key:"edit",icon:He(ma)},{label:"删除标签",key:"delete",icon:He(mt)}],U=b(ce.ADD),W=b(null),Z=b(!1),O=b(!1),k=na({tagId:-1,displayName:"",slug:"",color:""}),Ae=sa();oa(()=>{A();let i=Number(Ae.query.tagId);isNaN(i)||ba(Number(i)).then(c=>{$(c.data)}).catch(c=>be(c)),M()});const A=()=>{let i=Number(localStorage.getItem(Me.TAG_MODE.toString())??N.BLOCK);Object.values(N).includes(i)?r.value=i:r.value=N.BLOCK;let c=Number(localStorage.getItem(Me.TAG_PAGE_SIZE.toString())??10);isNaN(c)||c<10||c>120?o.value=10:o.value=c},M=()=>{window.$loadingBar.start(),r.value===N.BLOCK?st().then(i=>{var c;t.value=i.data.data,u.value=((c=t.value)==null?void 0:c.length)??0,window.$loadingBar.finish()}).catch(i=>{be(i),window.$loadingBar.error()}):F()},F=()=>{st(n.value,o.value).then(i=>{var V;let c=i.data;if(((V=c.data)==null?void 0:V.length)===0&&c.totalData!==0){n.value=1,F();return}t.value=c.data,u.value=c.totalData,d.value=c.totalPages,window.$loadingBar.finish()}).catch(i=>{be(i),window.$loadingBar.error()})},Ce=i=>{var V;let c=(V=t.value)==null?void 0:V[m.value];switch(i){case"delete":De(c);break;case"edit":$(c);break;case"viewPost":bt.push({name:kt.POST.name,query:{tagId:c.tagId}});break}},De=i=>{nt("确定要删除标签 ["+(i==null?void 0:i.displayName)+"] 吗？此操作不可逆。",()=>{Te([i.tagId])})},L=()=>{let i=Array();g.value.forEach(c=>{i.push(c)}),nt(`确定要删除选择的 ${i.length} 个标签吗？`,()=>{Te(i)})},Te=i=>{wa(i).then(()=>{Fe("删除成功"),g.value=g.value.filter(c=>!i.includes(c)),M()}).catch(c=>{be(c)})},$=i=>{H(),k.tagId=i.tagId,k.displayName=i.displayName,k.slug=i.slug,k.color=i.color??"",U.value=ce.EDIT,Z.value=!0},T=()=>{H(),U.value=ce.ADD,Z.value=!0},Y=()=>{var i;return(i=W.value)==null||i.validate(c=>{if(!c){O.value=!0;let V={tagId:k.tagId,displayName:k.displayName,slug:encodeURIComponent(k.slug),color:k.color};U.value===ce.ADD?ka(V).then(()=>{Fe("添加成功"),J()}).catch(ne=>{be(ne),O.value=!1}):xa(V).then(()=>{Fe("修改成功"),J()}).catch(ne=>{be(ne),O.value=!1})}}).catch(()=>{}),!1},J=()=>{O.value=!1,Z.value=!1,H(),M()},H=()=>{k.tagId=-1,k.displayName="",k.slug="",k.color=""},X=i=>{k.displayName=i,U.value===ce.ADD&&(k.slug=fa(i))},K=i=>{r.value=i,M(),localStorage.setItem(Me.TAG_MODE.toString(),i.toString())},R=i=>{n.value=i,M()},se=i=>{o.value=i,localStorage.setItem(Me.TAG_PAGE_SIZE.toString(),i.toString()),M()},Q=i=>{g.value.push(i.tagId)},Se=i=>{g.value=g.value.filter(c=>c!==i.tagId)},Be=()=>{var i;g.value=[],(i=t.value)==null||i.forEach(c=>{g.value.push(c.tagId)})},fe=()=>{g.value=[]};return(i,c)=>{var V,ne;return z(),G("div",ur,[p(h(ca),{show:Z.value,"onUpdate:show":c[3]||(c[3]=_=>Z.value=_),preset:"dialog",title:U.value==h(ce).ADD?"添加标签":"标签设置","positive-text":U.value==h(ce).ADD?"添加":"保存","negative-text":"取消",loading:O.value,onPositiveClick:Y,onKeydown:la(Y,["enter"])},{default:f(()=>[p(h(ua),{ref_key:"addEditDialogRef",ref:W,class:"dialog-form",model:k},{default:f(()=>[p(h(Le),{label:"标签名",path:"displayName",rule:{required:!0,message:"请输入标签名"}},{default:f(()=>[p(h(qe),{"default-value":k.displayName,"onUpdate:defaultValue":c[0]||(c[0]=_=>k.displayName=_),placeholder:"标签名",onUpdateValue:X,maxlength:"50"},null,8,["default-value"])]),_:1}),p(h(Le),{label:"别名",path:"slug",rule:{required:!0,message:"请输入标签别名"}},{default:f(()=>[p(h(qe),{value:k.slug,"onUpdate:value":c[1]||(c[1]=_=>k.slug=_),placeholder:"标签别名",maxlength:"50"},null,8,["value"])]),_:1}),p(h(Le),{label:"颜色",path:"color"},{default:f(()=>[p(h(Xa),{value:k.color,"onUpdate:value":c[2]||(c[2]=_=>k.color=_),actions:["clear"],modes:["hex"],"show-alpha":!1},null,8,["value"])]),_:1})]),_:1},8,["model"])]),_:1},8,["show","title","positive-text","loading"]),p(ia,{"data-count":u.value,"show-empty-status":t.value!==null&&t.value.length===0,"current-page":n.value,"page-size":o.value,"page-count":d.value,"current-page-item-count":((V=t.value)==null?void 0:V.length)??0,"show-pagination":r.value==h(N).LIST,"item-string":"标签",onOnPageUpdate:R,onOnPageSizeUpdate:se,"show-checkbox":"",onOnChecked:Be,onOnCheckboxCancel:fe,"is-checked":g.value.length===((ne=t.value)==null?void 0:ne.length)&&t.value.length!==0,"show-delete-button":g.value.length>0,onOnDeleteButtonClick:L},{"header-extra":f(()=>[p(h(ae),{type:"primary",onClick:T},{icon:f(()=>[p(h(Xe),null,{default:f(()=>[p(h(da))]),_:1})]),default:f(()=>[h(a).isSmallWindow?Ue("",!0):(z(),G("span",cr,"添加标签"))]),_:1})]),content:f(()=>[r.value===h(N).BLOCK?(z(),G("div",dr,[p(h(ot),null,{default:f(()=>[(z(!0),G(lt,null,it(t.value,(_,C)=>(z(),G("div",{onClick:et=>m.value=C},[p(h(ha),{trigger:"hover","keep-alive-on-hover":!1},{trigger:f(()=>[ze("div",null,[p(h(pa),{trigger:"click",options:E,"show-arrow":"",onSelect:Ce},{default:f(()=>[p(xt,{size:"medium",tag:_,pointer:""},null,8,["tag"])]),_:2},1024)])]),default:f(()=>[ze("span",null,vt(_.slug),1)]),_:2},1024)],8,hr))),256))]),_:1})])):Ue("",!0),r.value===h(N).LIST?(z(),G("div",pr,[p(h(ga),{hoverable:""},{default:f(()=>[(z(!0),G(lt,null,it(t.value,_=>(z(),ft(sr,{tag:_,"is-checked":g.value.includes(_.tagId),onOnDeleteTag:De,onOnEditTag:$,onOnChecked:Q,onOnUnChecked:Se},null,8,["tag","is-checked"]))),256))]),_:1})])):Ue("",!0)]),"action-left":f(()=>[p(h(ot),null,{default:f(()=>[p(h(ae),{class:"btn-switch-mode",circle:"",size:"small",secondary:r.value===h(N).BLOCK,onClick:c[4]||(c[4]=_=>K(h(N).BLOCK))},{icon:f(()=>[p(h(Qa))]),_:1},8,["secondary"]),p(h(ae),{class:"btn-switch-mode",circle:"",size:"small",secondary:r.value===h(N).LIST,onClick:c[5]||(c[5]=_=>K(h(N).LIST))},{icon:f(()=>[p(h(rr))]),_:1},8,["secondary"])]),_:1})]),_:1},8,["data-count","show-empty-status","current-page","page-size","page-count","current-page-item-count","show-pagination","is-checked","show-delete-button"])])}}});export{br as default};
