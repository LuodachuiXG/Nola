import{At as e,Dt as t,Et as n,H as r,Mt as i,Nt as a,Pt as o,V as s,c,cn as l,dn as u,en as d,jt as f,kt as p,un as m}from"./Wave-BaVmjZD-.js";import{S as h,b as g,y as _}from"./GlobalStore-sP_W_bFV.js";import{M as v}from"./apis-4W12woHh.js";import{B as y}from"./index-D6eSujYa.js";function b(e,t=`default`,n=[]){let{children:r}=e;if(typeof r==`object`&&r&&!Array.isArray(r)){let e=r[t];if(typeof e==`function`)return e()}return n}var x=n([t(`descriptions`,{fontSize:`var(--n-font-size)`},[t(`descriptions-separator`,`
 display: inline-block;
 margin: 0 8px 0 2px;
 `),t(`descriptions-table-wrapper`,[t(`descriptions-table`,[t(`descriptions-table-row`,[t(`descriptions-table-header`,{padding:`var(--n-th-padding)`}),t(`descriptions-table-content`,{padding:`var(--n-td-padding)`})])])]),f(`bordered`,[t(`descriptions-table-wrapper`,[t(`descriptions-table`,[t(`descriptions-table-row`,[n(`&:last-child`,[t(`descriptions-table-content`,{paddingBottom:0})])])])])]),e(`left-label-placement`,[t(`descriptions-table-content`,[n(`> *`,{verticalAlign:`top`})])]),e(`left-label-align`,[n(`th`,{textAlign:`left`})]),e(`center-label-align`,[n(`th`,{textAlign:`center`})]),e(`right-label-align`,[n(`th`,{textAlign:`right`})]),e(`bordered`,[t(`descriptions-table-wrapper`,`
 border-radius: var(--n-border-radius);
 overflow: hidden;
 background: var(--n-merged-td-color);
 border: 1px solid var(--n-merged-border-color);
 `,[t(`descriptions-table`,[t(`descriptions-table-row`,[n(`&:not(:last-child)`,[t(`descriptions-table-content`,{borderBottom:`1px solid var(--n-merged-border-color)`}),t(`descriptions-table-header`,{borderBottom:`1px solid var(--n-merged-border-color)`})]),t(`descriptions-table-header`,`
 font-weight: 400;
 background-clip: padding-box;
 background-color: var(--n-merged-th-color);
 `,[n(`&:not(:last-child)`,{borderRight:`1px solid var(--n-merged-border-color)`})]),t(`descriptions-table-content`,[n(`&:not(:last-child)`,{borderRight:`1px solid var(--n-merged-border-color)`})])])])])]),t(`descriptions-header`,`
 font-weight: var(--n-th-font-weight);
 font-size: 18px;
 transition: color .3s var(--n-bezier);
 line-height: var(--n-line-height);
 margin-bottom: 16px;
 color: var(--n-title-text-color);
 `),t(`descriptions-table-wrapper`,`
 transition:
 background-color .3s var(--n-bezier),
 border-color .3s var(--n-bezier);
 `,[t(`descriptions-table`,`
 width: 100%;
 border-collapse: separate;
 border-spacing: 0;
 box-sizing: border-box;
 `,[t(`descriptions-table-row`,`
 box-sizing: border-box;
 transition: border-color .3s var(--n-bezier);
 `,[t(`descriptions-table-header`,`
 font-weight: var(--n-th-font-weight);
 line-height: var(--n-line-height);
 display: table-cell;
 box-sizing: border-box;
 color: var(--n-th-text-color);
 transition:
 color .3s var(--n-bezier),
 background-color .3s var(--n-bezier),
 border-color .3s var(--n-bezier);
 `),t(`descriptions-table-content`,`
 vertical-align: top;
 line-height: var(--n-line-height);
 display: table-cell;
 box-sizing: border-box;
 color: var(--n-td-text-color);
 transition:
 color .3s var(--n-bezier),
 background-color .3s var(--n-bezier),
 border-color .3s var(--n-bezier);
 `,[p(`content`,`
 transition: color .3s var(--n-bezier);
 display: inline-block;
 color: var(--n-td-text-color);
 `)]),p(`label`,`
 font-weight: var(--n-th-font-weight);
 transition: color .3s var(--n-bezier);
 display: inline-block;
 margin-right: 14px;
 color: var(--n-th-text-color);
 `)])])])]),t(`descriptions-table-wrapper`,`
 --n-merged-th-color: var(--n-th-color);
 --n-merged-td-color: var(--n-td-color);
 --n-merged-border-color: var(--n-border-color);
 `),a(t(`descriptions-table-wrapper`,`
 --n-merged-th-color: var(--n-th-color-modal);
 --n-merged-td-color: var(--n-td-color-modal);
 --n-merged-border-color: var(--n-border-color-modal);
 `)),o(t(`descriptions-table-wrapper`,`
 --n-merged-th-color: var(--n-th-color-popover);
 --n-merged-td-color: var(--n-td-color-popover);
 --n-merged-border-color: var(--n-border-color-popover);
 `))]),S=`DESCRIPTION_ITEM_FLAG`;function C(e){return typeof e==`object`&&e&&!Array.isArray(e)?e.type&&e.type.DESCRIPTION_ITEM_FLAG:!1}u();var w=Object.assign(Object.assign({},c.props),{title:String,column:{type:Number,default:3},columns:Number,labelPlacement:{type:String,default:`top`},labelAlign:{type:String,default:`left`},separator:{type:String,default:`:`},size:{type:String,default:`medium`},bordered:Boolean,labelClass:String,labelStyle:[Object,String],contentClass:String,contentStyle:[Object,String]}),T=l({name:`Descriptions`,props:w,slots:Object,setup(e){let{mergedClsPrefixRef:t,inlineThemeDisabled:n}=r(e),a=c(`Descriptions`,`-descriptions`,x,y,e,t),o=d(()=>{let{size:t,bordered:n}=e,{common:{cubicBezierEaseInOut:r},self:{titleTextColor:o,thColor:s,thColorModal:c,thColorPopover:l,thTextColor:u,thFontWeight:d,tdTextColor:f,tdColor:p,tdColorModal:m,tdColorPopover:h,borderColor:g,borderColorModal:_,borderColorPopover:v,borderRadius:y,lineHeight:b,[i(`fontSize`,t)]:x,[i(n?`thPaddingBordered`:`thPadding`,t)]:S,[i(n?`tdPaddingBordered`:`tdPadding`,t)]:C}}=a.value;return{"--n-title-text-color":o,"--n-th-padding":S,"--n-td-padding":C,"--n-font-size":x,"--n-bezier":r,"--n-th-font-weight":d,"--n-line-height":b,"--n-th-text-color":u,"--n-td-text-color":f,"--n-th-color":s,"--n-th-color-modal":c,"--n-th-color-popover":l,"--n-td-color":p,"--n-td-color-modal":m,"--n-td-color-popover":h,"--n-border-radius":y,"--n-border-color":g,"--n-border-color-modal":_,"--n-border-color-popover":v}}),l=n?s(`descriptions`,d(()=>{let t=``,{size:n,bordered:r}=e;return r&&(t+=`a`),t+=n[0],t}),o,e):void 0;return{mergedClsPrefix:t,cssVars:n?void 0:o,themeClass:l?.themeClass,onRender:l?.onRender,compitableColumn:h(e,[`columns`,`column`]),inlineThemeDisabled:n}},render(){let e=this.$slots.default,t=e?g(e()):[];t.length;let{contentClass:n,labelClass:r,compitableColumn:i,labelPlacement:a,labelAlign:o,size:s,bordered:c,title:l,cssVars:u,mergedClsPrefix:d,separator:f,onRender:p}=this;p?.();let h=t.filter(e=>C(e)),y=h.reduce((e,t,o)=>{let s=t.props||{},l=h.length-1===o,u=[`label`in s?s.label:b(t,`label`)],p=[b(t)],g=s.span||1,_=e.span;e.span+=g;let v=s.labelStyle||s[`label-style`]||this.labelStyle,y=s.contentStyle||s[`content-style`]||this.contentStyle;if(a===`left`)c?e.row.push(m(`th`,{class:[`${d}-descriptions-table-header`,r],colspan:1,style:v},u),m(`td`,{class:[`${d}-descriptions-table-content`,n],colspan:l?(i-_)*2+1:g*2-1,style:y},p)):e.row.push(m(`td`,{class:`${d}-descriptions-table-content`,colspan:l?(i-_)*2:g*2},m(`span`,{class:[`${d}-descriptions-table-content__label`,r],style:v},[...u,f&&m(`span`,{class:`${d}-descriptions-separator`},f)]),m(`span`,{class:[`${d}-descriptions-table-content__content`,n],style:y},p)));else{let t=l?(i-_)*2:g*2;e.row.push(m(`th`,{class:[`${d}-descriptions-table-header`,r],colspan:t,style:v},u)),e.secondRow.push(m(`td`,{class:[`${d}-descriptions-table-content`,n],colspan:t,style:y},p))}return(e.span>=i||l)&&(e.span=0,e.row.length&&(e.rows.push(e.row),e.row=[]),a!==`left`&&e.secondRow.length&&(e.rows.push(e.secondRow),e.secondRow=[])),e},{span:0,row:[],secondRow:[],rows:[]}).rows.map(e=>m(`tr`,{class:`${d}-descriptions-table-row`},e));return m(`div`,{style:u,class:[`${d}-descriptions`,this.themeClass,`${d}-descriptions--${a}-label-placement`,`${d}-descriptions--${o}-label-align`,`${d}-descriptions--${s}-size`,c&&`${d}-descriptions--bordered`]},l||this.$slots.header?m(`div`,{class:`${d}-descriptions-header`},l||_(this,`header`)):null,m(`div`,{class:`${d}-descriptions-table-wrapper`},m(`table`,{class:`${d}-descriptions-table`},m(`tbody`,null,a===`top`&&m(`tr`,{class:`${d}-descriptions-table-row`,style:{visibility:`collapse`}},v(i*2,m(`td`,null))),y))))}});u();var E={label:String,span:{type:Number,default:1},labelClass:String,labelStyle:[Object,String],contentClass:String,contentStyle:[Object,String]},D=l({name:`DescriptionsItem`,[S]:!0,props:E,slots:Object,render(){return null}});export{T as n,D as t};