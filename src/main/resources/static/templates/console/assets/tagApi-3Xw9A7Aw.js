import{c as i}from"./TrashOutline-DLK0KsOh.js";import{d as g,e as d,o as m,c,a as p,w as f,A as T,G as v,b8 as E,n as h,u as y,s as o}from"./index-BeAjLgC_.js";const C=g({__name:"MyTag",props:{tag:{},size:{},pointer:{type:Boolean}},emits:["onMouseEnterTag","onMouseLeaveTag"],setup(a,{emit:s}){const t=d(!1),n=a,r=s,l=()=>{t.value=!0,r("onMouseEnterTag",n.tag)},u=()=>{t.value=!1,r("onMouseLeaveTag",n.tag)};return(e,M)=>(m(),c("div",{onMouseenter:l,onMouseleave:u},[p(y(i),{class:"transition",style:h(e.pointer?"cursor: pointer;":""),size:e.size,color:e.tag.color!==null&&e.tag.color!==""&&!t.value?{textColor:e.tag.color,borderColor:e.tag.color}:{}},{default:f(()=>[T("span",{class:E(e.tag.color!==null&&e.tag.color!==""&&!t.value?"tag-text-shadow":"")},v(e.tag.displayName),3)]),_:1},8,["style","size","color"])],32))}});function L(a){return o({url:"/admin/tag",method:"POST",data:a})}function N(a){return o({url:"/admin/tag",method:"DELETE",data:a})}function b(a){return o({url:"/admin/tag",method:"PUT",data:a})}function w(a){return o({url:`/admin/tag/${a}`,method:"GET"})}function G(a=null,s=null){return o({url:`/admin/tag?page=${a??0}&size=${s??0}`,method:"GET"})}export{C as _,L as a,w as b,N as d,G as t,b as u};
