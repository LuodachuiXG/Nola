import{At as e,Dt as t,Et as n,Fn as r,H as i,Hn as a,Ht as o,K as s,Mt as c,V as l,Vt as u,Wt as d,c as f,cn as p,d as m,dn as h,en as g,et as _,gn as v,i as y,jn as b,kt as x,l as S,n as C,q as w,r as T,t as E,u as D,un as O,xn as k,zn as A}from"./Wave-BaVmjZD-.js";import{m as j,x as M}from"./GlobalStore-sP_W_bFV.js";h(),r();var N=p({name:`SlotMachineNumber`,props:{clsPrefix:{type:String,required:!0},value:{type:[Number,String],required:!0},oldOriginalNumber:{type:Number,default:void 0},newOriginalNumber:{type:Number,default:void 0}},setup(e){let t=A(null),n=A(e.value),r=A(e.value),i=A(`up`),o=A(!1),s=g(()=>o.value?`${e.clsPrefix}-base-slot-machine-current-number--${i.value}-scroll`:null),c=g(()=>o.value?`${e.clsPrefix}-base-slot-machine-old-number--${i.value}-scroll`:null);b(a(e,`value`),(e,t)=>{n.value=t,r.value=e,v(l)});function l(){let t=e.newOriginalNumber,n=e.oldOriginalNumber;n!==void 0&&t!==void 0&&(t>n?u(`up`):n>t&&u(`down`))}function u(e){i.value=e,o.value=!1,v(()=>{var e;(e=t.value)==null||e.offsetWidth,o.value=!0})}return()=>{let{clsPrefix:i}=e;return O(`span`,{ref:t,class:`${i}-base-slot-machine-number`},n.value===null?null:O(`span`,{class:[`${i}-base-slot-machine-old-number ${i}-base-slot-machine-old-number--top`,c.value]},n.value),O(`span`,{class:[`${i}-base-slot-machine-current-number`,s.value]},O(`span`,{ref:`numberWrapper`,class:[`${i}-base-slot-machine-current-number__inner`,typeof e.value!=`number`&&`${i}-base-slot-machine-current-number__inner--not-number`]},r.value)),n.value===null?null:O(`span`,{class:[`${i}-base-slot-machine-old-number ${i}-base-slot-machine-old-number--bottom`,c.value]},n.value))}}}),{cubicBezierEaseOut:P}=D;function F({duration:e=`.2s`}={}){return[n(`&.fade-up-width-expand-transition-leave-active`,{transition:`
 opacity ${e} ${P},
 max-width ${e} ${P},
 transform ${e} ${P}
 `}),n(`&.fade-up-width-expand-transition-enter-active`,{transition:`
 opacity ${e} ${P},
 max-width ${e} ${P},
 transform ${e} ${P}
 `}),n(`&.fade-up-width-expand-transition-enter-to`,{opacity:1,transform:`translateX(0) translateY(0)`}),n(`&.fade-up-width-expand-transition-enter-from`,{maxWidth:`0 !important`,opacity:0,transform:`translateY(60%)`}),n(`&.fade-up-width-expand-transition-leave-from`,{opacity:1,transform:`translateY(0)`}),n(`&.fade-up-width-expand-transition-leave-to`,{maxWidth:`0 !important`,opacity:0,transform:`translateY(60%)`})]}var I=n([n(`@keyframes n-base-slot-machine-fade-up-in`,`
 from {
 transform: translateY(60%);
 opacity: 0;
 }
 to {
 transform: translateY(0);
 opacity: 1;
 }
 `),n(`@keyframes n-base-slot-machine-fade-down-in`,`
 from {
 transform: translateY(-60%);
 opacity: 0;
 }
 to {
 transform: translateY(0);
 opacity: 1;
 }
 `),n(`@keyframes n-base-slot-machine-fade-up-out`,`
 from {
 transform: translateY(0%);
 opacity: 1;
 }
 to {
 transform: translateY(-60%);
 opacity: 0;
 }
 `),n(`@keyframes n-base-slot-machine-fade-down-out`,`
 from {
 transform: translateY(0%);
 opacity: 1;
 }
 to {
 transform: translateY(60%);
 opacity: 0;
 }
 `),t(`base-slot-machine`,`
 overflow: hidden;
 white-space: nowrap;
 display: inline-block;
 height: 18px;
 line-height: 18px;
 `,[t(`base-slot-machine-number`,`
 display: inline-block;
 position: relative;
 height: 18px;
 width: .6em;
 max-width: .6em;
 `,[F({duration:`.2s`}),C({duration:`.2s`,delay:`0s`}),t(`base-slot-machine-old-number`,`
 display: inline-block;
 opacity: 0;
 position: absolute;
 left: 0;
 right: 0;
 `,[e(`top`,{transform:`translateY(-100%)`}),e(`bottom`,{transform:`translateY(100%)`}),e(`down-scroll`,{animation:`n-base-slot-machine-fade-down-out .2s cubic-bezier(0, 0, .2, 1)`,animationIterationCount:1}),e(`up-scroll`,{animation:`n-base-slot-machine-fade-up-out .2s cubic-bezier(0, 0, .2, 1)`,animationIterationCount:1})]),t(`base-slot-machine-current-number`,`
 display: inline-block;
 position: absolute;
 left: 0;
 top: 0;
 bottom: 0;
 right: 0;
 opacity: 1;
 transform: translateY(0);
 width: .6em;
 `,[e(`down-scroll`,{animation:`n-base-slot-machine-fade-down-in .2s cubic-bezier(0, 0, .2, 1)`,animationIterationCount:1}),e(`up-scroll`,{animation:`n-base-slot-machine-fade-up-in .2s cubic-bezier(0, 0, .2, 1)`,animationIterationCount:1}),x(`inner`,`
 display: inline-block;
 position: absolute;
 right: 0;
 top: 0;
 width: .6em;
 `,[e(`not-number`,`
 right: unset;
 left: 0;
 `)])])])])]);h(),r(),d();var L=p({name:`BaseSlotMachine`,props:{clsPrefix:{type:String,required:!0},value:{type:[Number,String],default:0},max:{type:Number,default:void 0},appeared:{type:Boolean,required:!0}},setup(e){S(`-base-slot-machine`,I,a(e,`clsPrefix`));let t=A(),n=A(),r=g(()=>{if(typeof e.value==`string`)return[];if(e.value<1)return[0];let t=[],n=e.value;for(e.max!==void 0&&(n=Math.min(e.max,n));n>=1;)t.push(n%10),n/=10,n=Math.floor(n);return t.reverse(),t});return b(a(e,`value`),(e,r)=>{typeof e==`string`?(n.value=void 0,t.value=void 0):typeof r==`string`?(n.value=e,t.value=void 0):(n.value=e,t.value=r)}),()=>{let{value:i,clsPrefix:a}=e;return typeof i==`number`?O(`span`,{class:`${a}-base-slot-machine`},O(o,{name:`fade-up-width-expand-transition`,tag:`span`},{default:()=>r.value.map((e,i)=>O(N,{clsPrefix:a,key:r.value.length-i-1,oldOriginalNumber:t.value,newOriginalNumber:n.value,value:e}))}),O(y,{key:`+`,width:!0},{default:()=>e.max!==void 0&&e.max<i?O(N,{clsPrefix:a,value:`+`}):null})):O(`span`,{class:`${a}-base-slot-machine`},i)}}});function R(e){let{errorColor:t,infoColor:n,successColor:r,warningColor:i,fontFamily:a}=e;return{color:t,colorInfo:n,colorSuccess:r,colorError:t,colorWarning:i,fontSize:`12px`,fontFamily:a}}var z={name:`Badge`,common:T,self:R},B=n([n(`@keyframes badge-wave-spread`,{from:{boxShadow:`0 0 0.5px 0px var(--n-ripple-color)`,opacity:.6},to:{boxShadow:`0 0 0.5px 4.5px var(--n-ripple-color)`,opacity:0}}),t(`badge`,`
 display: inline-flex;
 position: relative;
 vertical-align: middle;
 font-family: var(--n-font-family);
 `,[e(`as-is`,[t(`badge-sup`,{position:`static`,transform:`translateX(0)`},[j({transformOrigin:`left bottom`,originalTransform:`translateX(0)`})])]),e(`dot`,[t(`badge-sup`,`
 height: 8px;
 width: 8px;
 padding: 0;
 min-width: 8px;
 left: 100%;
 bottom: calc(100% - 4px);
 `,[n(`::before`,`border-radius: 4px;`)])]),t(`badge-sup`,`
 background: var(--n-color);
 transition:
 background-color .3s var(--n-bezier),
 color .3s var(--n-bezier);
 color: #FFF;
 position: absolute;
 height: 18px;
 line-height: 18px;
 border-radius: 9px;
 padding: 0 6px;
 text-align: center;
 font-size: var(--n-font-size);
 transform: translateX(-50%);
 left: 100%;
 bottom: calc(100% - 9px);
 font-variant-numeric: tabular-nums;
 z-index: 2;
 display: flex;
 align-items: center;
 `,[j({transformOrigin:`left bottom`,originalTransform:`translateX(-50%)`}),t(`base-wave`,{zIndex:1,animationDuration:`2s`,animationIterationCount:`infinite`,animationDelay:`1s`,animationTimingFunction:`var(--n-ripple-bezier)`,animationName:`badge-wave-spread`}),n(`&::before`,`
 opacity: 0;
 transform: scale(1);
 border-radius: 9px;
 content: "";
 position: absolute;
 left: 0;
 right: 0;
 top: 0;
 bottom: 0;
 `)])])]);h(),r(),d();var V=Object.assign(Object.assign({},f.props),{value:[String,Number],max:Number,dot:Boolean,type:{type:String,default:`default`},show:{type:Boolean,default:!0},showZero:Boolean,processing:Boolean,color:String,offset:Array}),H=p({name:`Badge`,props:V,setup(e,{slots:t}){let{mergedClsPrefixRef:n,inlineThemeDisabled:r,mergedRtlRef:a}=i(e),o=f(`Badge`,`-badge`,B,z,e,n),u=A(!1),d=()=>{u.value=!0},p=()=>{u.value=!1},h=g(()=>e.show&&(e.dot||e.value!==void 0&&!(!e.showZero&&Number(e.value)<=0)||!s(t.value)));k(()=>{h.value&&(u.value=!0)});let v=m(`Badge`,a,n),y=g(()=>{let{type:t,color:n}=e,{common:{cubicBezierEaseInOut:r,cubicBezierEaseOut:i},self:{[c(`color`,t)]:a,fontFamily:s,fontSize:l}}=o.value;return{"--n-font-size":l,"--n-font-family":s,"--n-color":n||a,"--n-ripple-color":n||a,"--n-bezier":r,"--n-ripple-bezier":i}}),b=r?l(`badge`,g(()=>{let t=``,{type:n,color:r}=e;return n&&(t+=n[0]),r&&(t+=_(r)),t}),y,e):void 0,x=g(()=>{let{offset:t}=e;if(!t)return;let[n,r]=t,i=typeof n==`number`?`${n}px`:n,a=typeof r==`number`?`${r}px`:r;return{transform:`translate(calc(${v?.value?`50%`:`-50%`} + ${i}), ${a})`}});return{rtlEnabled:v,mergedClsPrefix:n,appeared:u,showBadge:h,handleAfterEnter:d,handleAfterLeave:p,cssVars:r?void 0:y,themeClass:b?.themeClass,onRender:b?.onRender,offsetStyle:x}},render(){let{mergedClsPrefix:e,onRender:t,themeClass:n,$slots:r}=this;t?.();let i=r.default?.call(r);return O(`div`,{class:[`${e}-badge`,this.rtlEnabled&&`${e}-badge--rtl`,n,{[`${e}-badge--dot`]:this.dot,[`${e}-badge--as-is`]:!i}],style:this.cssVars},i,O(u,{name:`fade-in-scale-up-transition`,onAfterEnter:this.handleAfterEnter,onAfterLeave:this.handleAfterLeave},{default:()=>this.showBadge?O(`sup`,{class:`${e}-badge-sup`,title:M(this.value),style:this.offsetStyle},w(r.value,()=>[this.dot?null:O(L,{clsPrefix:e,appeared:this.appeared,max:this.max,value:this.value})]),this.processing?O(E,{clsPrefix:e}):null):null}))}});export{H as t};