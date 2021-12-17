export const getThumbnailFromRaw= (img, size)=>{
	let thumbnail=new Image();
	thumbnail.src=img;
	const canvas=document.createElement('canvas');
	const context=canvas.getContext('2d');
	canvas.width=size;
	canvas.height=size;
	context.drawImage(thumbnail, 0, 0, size, size);
	return canvas.toDataURL('image/jpeg', 1);
}